package pinetree.lifenavi.view;


import android.content.Context;
import android.opengl.GLES30;
import android.util.AttributeSet;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.utils.Constant;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class BallGLSurfaceView extends BaseGLSurfaceView {


    public BallGLSurfaceView(Context context) {
        super(context);
    }

    public BallGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }


}
