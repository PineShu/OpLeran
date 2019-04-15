package pinetree.lifenavi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    public  static Bitmap  BitmapFactory(Context glSurfaceView, int resId){
        InputStream is = glSurfaceView.getResources().openRawResource(resId);
        Bitmap bitmapTmp;
        try
        {
            bitmapTmp = BitmapFactory.decodeStream(is);
        }
        finally
        {
            try
            {
                is.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
      	  //纹理加载成功后释放内存中的纹理图
        return bitmapTmp;
    }
}
