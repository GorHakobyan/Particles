package com.html.gmbrdilos.particles;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.html.gmbrdilos.particles.objects.ParticleShooter;
import com.html.gmbrdilos.particles.objects.ParticleSystem;
import com.html.gmbrdilos.particles.programs.ParticleShaderProgram;
import com.html.gmbrdilos.particles.util.Geometry;
import com.html.gmbrdilos.particles.util.MatrixHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




public class ParticlesRenderer implements Renderer
{


    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];

    private ParticleShaderProgram particleProgram;

    private ParticleSystem particleSystem;

    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;
    private long globalStartTime;

    public ParticlesRenderer(Context context)
    {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
    {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

//        OpenGL’s default blending equation:
//        output = (source factor * source fragment) + (destination factor * destination fragment)

//        In OpenGL, blending works by blending the result of the fragment shader
//        with the color that’s already there in the frame buffer. The value for source
//        fragment comes from our fragment shader, destination fragment is what’s
//        already there in the frame buffer, and the values for source factor and destination
//        factor are configured by calling glBlendFunc(). In the code that we just
//        added, we called glBlendFunc() with each factor set to GL_ONE, which changes the
//        blending equation as follows:
//
//        output = (GL_ONE * source fragment) + (GL_ONE * destination fragment)
//
//        GL_ONE is just a placeholder for 1, and since multiplying anything by 1 results
//        in the same number, the equation can be simplified as follows:
//
//        output = source fragment + destination fragment
//
//        With this blending mode, the fragments from our fragment shader will be
//        added to the fragments already on the screen, and that’s how we get additive
//        blending.

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE);

        particleProgram = new ParticleShaderProgram(context);
        particleSystem = new ParticleSystem(10000);
        globalStartTime = System.nanoTime();

        final Geometry.Vector particleDirection = new Geometry.Vector(0f, 0.5f, 0f);

        final float angleVarianceInDegrees = 5f;
        final float speedVariance = 1f;


//        The next part of the method sets up our three particle fountains. Each fountain
//        is represented by a particle shooter, and each shooter will shoot its particles
//        in the direction of particleDirection, or straight up along the y-axis. We’ve aligned
//        the three fountains from left to right, and we’ve set the colors so that the first
//        one is red, the second is green, and the third is blue.

        redParticleShooter = new ParticleShooter(
                new Geometry.Point(-1f, 0f, 0f),
                particleDirection,
                Color.rgb(255, 50, 5),
                angleVarianceInDegrees,
                speedVariance);

        greenParticleShooter = new ParticleShooter(
                new Geometry.Point(0f, 0f, 0f),
                particleDirection,
                Color.rgb(25, 255, 25),
                angleVarianceInDegrees,
                speedVariance);

        blueParticleShooter = new ParticleShooter(
                new Geometry.Point(1f, 0f, 0f),
                particleDirection,
                Color.rgb(5, 50, 255),
                angleVarianceInDegrees,
                speedVariance);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);

        Matrix.setIdentityM(viewMatrix, 0);
        Matrix.translateM(viewMatrix, 0, 0f, -1.5f, -5f);
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        float currentTime = (System.nanoTime() - globalStartTime) / 1000000000f;

        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);

        particleProgram.useProgram();
        particleProgram.setUniforms(viewProjectionMatrix, currentTime);
        particleSystem.bindData(particleProgram);
        particleSystem.draw();
    }
}