package com.html.gmbrdilos.particles.programs;


import android.content.Context;
import android.opengl.GLES20;

import com.html.gmbrdilos.particles.R;

public class ParticleShaderProgram extends ShaderProgram
{
//   Uniform locations
    private final int uMatrixLocation;
    private final int uTimeLocation;

//   Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;
    private final int aDirectionVectorLocation;
    private final int aParticleStartTimeLocation;

    public ParticleShaderProgram(Context context)
    {
        super(context, R.raw.particle_vertex_shader, R.raw.particle_fragment_shader);

//       Retrieve uniform locations for the shader program.
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTimeLocation = GLES20.glGetUniformLocation(program, U_TIME);

//       Retrieve attribute locations for the shader program.
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        aDirectionVectorLocation = GLES20.glGetAttribLocation(program, A_DIRECTION_VECTOR);
        aParticleStartTimeLocation = GLES20.glGetAttribLocation(program, A_PARTICLE_START_TIME);
    }

    public void setUniforms(float[] matrix, float elapsedTime)
    {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glUniform1f(uTimeLocation, elapsedTime);
    }

    public int getPositionAttributeLocation()
    {
        return aPositionLocation;
    }

    public int getColorAttributeLocation()
    {
        return aColorLocation;
    }

    public int getDirectionVectorAttributeLocation()
    {
        return aDirectionVectorLocation;
    }

    public int getParticleStartTimeAttributeLocation()
    {
        return aParticleStartTimeLocation;
    }
}
