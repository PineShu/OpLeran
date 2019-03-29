package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.utils.MatrixHelper;

/**
 * Created by shisk on 2019/3/29.
 */

public class fiveStarSurfaceView extends GLSurfaceView {
    public fiveStarSurfaceView(Context context) {
        super(context);
    }

    public fiveStarSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private class StarRender implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
            float ration = width / height;
            MatrixHelper.setOrthoM(0, -ration, ration, -1, 1, 1, 10);

            MatrixHelper.setLookAt(0,0,0,3f,0,0,0,0,1f,0);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

        }
    }


}
