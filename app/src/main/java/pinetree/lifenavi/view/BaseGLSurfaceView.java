package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

public abstract class BaseGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    public BaseGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public BaseGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
