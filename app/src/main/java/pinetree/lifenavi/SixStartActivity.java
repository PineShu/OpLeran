package pinetree.lifenavi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import pinetree.lifenavi.view.SixStarSurfaceView;

public class  SixStartActivity extends Activity {

    private SixStarSurfaceView sixStarSurfaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sixStarSurfaceView = new SixStarSurfaceView(this);
        sixStarSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setContentView(sixStarSurfaceView);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sixStarSurfaceView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sixStarSurfaceView.onPause();
    }
}
