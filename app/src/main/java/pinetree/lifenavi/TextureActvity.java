package pinetree.lifenavi;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import pinetree.lifenavi.view.TextureGLSurfaceView;

/**
 * Created by shisk on 2019/4/3.
 */

public class TextureActvity extends Activity {

    private TextureGLSurfaceView cubeGLSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cubeGLSurfaceView = new TextureGLSurfaceView(this);
        // 设置为全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 设置为横屏模式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
