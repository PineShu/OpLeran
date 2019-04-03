package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.shader.Cube;
import pinetree.lifenavi.utils.MatrixHelper;

/**
 * Created by shisk on 2019/4/3.
 */

public class CubeGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private Cube cube;

    public CubeGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public CubeGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        cube = new Cube(this);
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES30.glEnable(GLES30.GL_CULL_FACE);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float ratio = width / (float) height;
        MatrixHelper.setFrust(0, -ratio*0.8f, ratio*1.2f, -1, 1, 20, 100);
        MatrixHelper.setLookAt(0, -16f, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        MatrixHelper.setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        //绘制原立方体
        MatrixHelper.pushMatrix();//保护现场
        MatrixHelper.translate(0f, -1, 0);//沿x方向平移3.5
        cube.draw();//绘制立方体
        MatrixHelper.popMatrix();//恢复现场

        //绘制变换后的立方体
        MatrixHelper.pushMatrix();//保护现场
        MatrixHelper.translate(1f, 1.2f, 0);//沿x方向平移3.5
        cube.draw();//绘制立方体
        MatrixHelper.popMatrix();//恢复现场
    }
}
