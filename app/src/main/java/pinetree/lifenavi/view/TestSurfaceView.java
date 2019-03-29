package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by shisk on 2019/3/29.
 */

public class TestSurfaceView extends GLSurfaceView {
    public TestSurfaceView(Context context) {
        super(context);
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private class MRender implements Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            /**
             * 3d物体最终会映射到2D平面上，就是此接口指定的屏幕区域，左下角为屏幕原点
             */
            gl.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }
}
