package pinetree.lifenavi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.R;
import pinetree.lifenavi.shader.TextureTrangle;
import pinetree.lifenavi.utils.BitmapUtils;
import pinetree.lifenavi.utils.MatrixHelper;

public class TextureGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private int textureID;
    private final float TOUCH_SCALE_FACTOR = 180 / 320f;
    private TextureTrangle texRect;

    public TextureGLSurfaceView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public TextureGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    private void initTextuer() {

        Bitmap bitmap = BitmapUtils.BitmapFactory(this.getContext(), R.mipmap.wall);
        int txetUre[] = new int[1];
        /**
         * 生成纹理id
         * @param  产生纹理ID的数量
         * @param  texture 纹理ID存放的地址
         * @param  offest 偏移量
         */
        GLES30.glGenTextures(1, txetUre, 0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureID);//绑定纹理ID
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);//纹理采样
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);//s轴的延伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);//t轴的延伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_SWIZZLE_R,GLES30.GL_GREEN);//纹理图的绿色通道填充 采样器的红色通道
        /**
         * @param 纹理类型
         * @param level 纹理层级，
         *
         */
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //创建三角形对对象
        texRect = new TextureTrangle(this);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //初始化纹理
        initTextuer();
        //关闭背面剪裁
        GLES30.glDisable(GLES30.GL_CULL_FACE);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视窗大小及位置
        GLES30.glViewport(0, 0, width, height);
        //计算GLSurfaceView的宽高比
        float ratio = (float) width / height;
        //调用此方法计算产生透视投影矩阵
        MatrixHelper.setFrust(0,-ratio, ratio, -1, 1, 1, 10);
        //调用此方法产生摄像机9参数位置矩阵
        MatrixHelper.setLookAt(0,0,0,3,0f,0f,0f,0f,1.0f,0.0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除深度缓冲与颜色缓冲
        GLES30.glClear( GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        //绘制纹理三角形
        texRect.draw(textureID);
    }
}
