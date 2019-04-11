package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.Circle;
import pinetree.lifenavi.shader.Blert;
import pinetree.lifenavi.utils.MatrixHelper;

public class CircleSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private Blert blert;
    private Circle circle;

    public CircleSurfaceView(Context context) {
        super(context);
        init();
    }

    public CircleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setEGLContextClientVersion(3);
        setRenderer(this);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        circle = new Circle(this);

        blert = new Blert(this);

        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glEnable(GLES30.GL_CULL_FACE);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        MatrixHelper.setFrust(0, -ratio, ratio, -1, 1, 20, 100);
        MatrixHelper.setLookAt(0,0,0,30,0,0,0,0,1,0);
        MatrixHelper.setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        MatrixHelper.pushMatrix();
        MatrixHelper.pushMatrix();
        MatrixHelper.translate(-1.3f, 0, 0);
        blert.draw();
        MatrixHelper.popMatrix();


        MatrixHelper.pushMatrix();
        MatrixHelper.translate(1.3f, 0, 0);
        circle.draw();
        MatrixHelper.popMatrix();
        MatrixHelper.popMatrix();

    }
}
