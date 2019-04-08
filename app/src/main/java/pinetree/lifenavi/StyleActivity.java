package pinetree.lifenavi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pinetree.lifenavi.view.StyleGlSurfaceView;

public class StyleActivity extends Activity {

    private StyleGlSurfaceView glSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new StyleGlSurfaceView(this);
        setContentView(glSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }
}
