package pinetree.lifenavi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pinetree.lifenavi.view.CubeGLSurfaceView;

/**
 * Created by shisk on 2019/4/3.
 */

public class CubeActvity extends Activity {

    private CubeGLSurfaceView cubeGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cubeGLSurfaceView = new CubeGLSurfaceView(this);
        setContentView(cubeGLSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cubeGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cubeGLSurfaceView.onPause();
    }
}
