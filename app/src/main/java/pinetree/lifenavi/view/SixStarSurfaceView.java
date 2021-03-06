package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.shader.FiveStart;
import pinetree.lifenavi.utils.MatrixHelper;

/**
 * Created by shisk on 2019/3/29.
 */

public class SixStarSurfaceView extends GLSurfaceView {
    public SixStarSurfaceView(Context context) {
        super(context);
        init();
    }

    public SixStarSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(3);
        setRenderer(new StarRender());
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


    private class StarRender implements GLSurfaceView.Renderer {
        FiveStart[] ha = new FiveStart[6];//六角星数组

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            //创建六角星数组中的各个六角星
            for (int i = 0; i < ha.length; i++) {
                ha[i] = new FiveStart(SixStarSurfaceView.this, 0.2f, 0.5f, -0.3f * i);
            }
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }


        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            float ration = width /(float) height;
            MatrixHelper.setOrthoM(0, -ration, ration, -1, 1, 1, 10);

            MatrixHelper.setLookAt(0, 0, 0, 3f, 0, 0, 0, 0, 1f, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //循环绘制各个六角星
            for (FiveStart h : ha) {
                h.draw();
            }
        }
    }


}
