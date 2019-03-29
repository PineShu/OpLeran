package pinetree.lifenavi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import pinetree.lifenavi.view.SqureSurfaceView;

/**
 * Created by shisk on 2019/3/20.
 */

public class TutoriaPart extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); // (NEW)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // (NEW)
        SqureSurfaceView view = new SqureSurfaceView(this);
        setContentView(view);
    }

}
