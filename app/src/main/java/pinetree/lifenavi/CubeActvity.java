package pinetree.lifenavi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pinetree.lifenavi.view.CubeGLSurfaceView;

/**
 * Created by shisk on 2019/4/3.
 */

public class CubeActvity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CubeGLSurfaceView glSurfaceView = new CubeGLSurfaceView(this);
        setContentView(glSurfaceView);
    }
}
