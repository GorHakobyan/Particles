package com.html.gmbrdilos.particles;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;




public class ParticlesRenderer implements Renderer
{

    private final Context context;

    public ParticlesRenderer(Context context)
    {
        this.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig)
    {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1)
    {

    }

    @Override
    public void onDrawFrame(GL10 gl10)
    {

    }
}