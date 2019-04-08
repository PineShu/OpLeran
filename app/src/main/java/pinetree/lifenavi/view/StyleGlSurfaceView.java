package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.shader.DrawStyle;
import pinetree.lifenavi.utils.MatrixHelper;

public class StyleGlSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    DrawStyle drawStyle;

    public StyleGlSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        drawStyle = new DrawStyle(this);
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0, 0, 0, 1.0f);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES30.glEnable(GLES30.GL_CULL_FACE);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
        MatrixHelper.setFrust(0, -ratio, ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机矩阵
        MatrixHelper.setLookAt(0, 0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        //初始化变换矩阵
        MatrixHelper.setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        //保护现场
        MatrixHelper.pushMatrix();
        drawStyle.draw(GLES30.GL_POINTS);
        //恢复现场
        MatrixHelper.popMatrix();
    }
}
