package com.html.gmbrdilos.particles.objects;

import android.graphics.Color;
import android.opengl.GLES20;

import com.html.gmbrdilos.particles.Constants;
import com.html.gmbrdilos.particles.data.VertexArray;
import com.html.gmbrdilos.particles.programs.ParticleShaderProgram;
import com.html.gmbrdilos.particles.util.Geometry;

public class ParticleSystem
{
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;

    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT
            + COLOR_COMPONENT_COUNT
            + VECTOR_COMPONENT_COUNT
            + PARTICLE_START_TIME_COMPONENT_COUNT;

    private static final int STRIDE = TOTAL_COMPONENT_COUNT * Constants.BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

//       currentParticleCount and nextParticle to keep track of the particles in the array.
    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount)
    {
        particles = new float[maxParticleCount * TOTAL_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    public void addParticle(Geometry.Point position, int color, Geometry.Vector direction, float particleStartTime)
    {
//        particleOffset to remember where our new particle started
        final int particleOffset = nextParticle * TOTAL_COMPONENT_COUNT;

//        currentOffset to remember the position for each attribute of the new particle.
        int currentOffset = particleOffset;
        nextParticle++;

        if (currentParticleCount < maxParticleCount)
        {
//            We also need to keep track of how many particles need to be drawn, and we
//            do this by incrementing currentParticleCount each time a new particle is added
            currentParticleCount++;
        }

        if (nextParticle == maxParticleCount)
        {
//       Start over at the beginning, but keep currentParticleCount so
//       that all the other particles still get drawn.

//       When we reach the end, we start over at 0 so we can recycle the oldest particles
            nextParticle = 0;
        }

        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;

        particles[currentOffset++] = Color.red(color) / 255f;
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;

        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;

        particles[currentOffset++] = particleStartTime;

//        We want to copy over only the new data so that we don’t waste time copying
//        over data that hasn’t changed, so we pass in the start offset for the new particle
//        and the count
        vertexArray.updateBuffer(particles, particleOffset, TOTAL_COMPONENT_COUNT);
    }

    public void bindData(ParticleShaderProgram particleProgram)
    {
        int dataOffset = 0;

        vertexArray.setVertexAttribPointer(
                dataOffset,
                particleProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT,
                STRIDE);

        dataOffset += POSITION_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(
                dataOffset,
                particleProgram.getColorAttributeLocation(),
                COLOR_COMPONENT_COUNT,
                STRIDE);

        dataOffset += COLOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(
                dataOffset,
                particleProgram.getDirectionVectorAttributeLocation(),
                VECTOR_COMPONENT_COUNT,
                STRIDE);

        dataOffset += VECTOR_COMPONENT_COUNT;

        vertexArray.setVertexAttribPointer(
                dataOffset,
                particleProgram.getParticleStartTimeAttributeLocation(),
                PARTICLE_START_TIME_COMPONENT_COUNT,
                STRIDE);
    }

    public void draw()
    {
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, currentParticleCount);
    }
}
