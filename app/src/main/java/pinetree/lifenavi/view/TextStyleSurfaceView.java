package pinetree.lifenavi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.R;
import pinetree.lifenavi.shader.TextureStyle;
import pinetree.lifenavi.utils.BitmapUtils;
import pinetree.lifenavi.utils.MatrixHelper;

public class TextStyleSurfaceView extends BaseGLSurfaceView {

    public int textureCTId, textureREId, textureMIId, currTextureId;
    public TextureStyle[] texRect=new TextureStyle[3];//纹理矩形数组
    public int trIndex=2;//当前纹理矩形索引

    public TextStyleSurfaceView(Context context) {
        super(context);
    }

    public TextStyleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f,0.5f,0.5f, 1.0f);
        //创建三个纹理矩形对对象
        texRect[0]=new TextureStyle(1,1,this);
        texRect[1]=new TextureStyle(4,2,this);
        texRect[2]=new TextureStyle(4,4,this);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //初始化系统分配的拉伸纹理id
        textureCTId=initTextureId(1);
        //初始化系统分配的重复纹理id
        textureREId=initTextureId(0);
        //初始化系统分配的镜像纹理id
        textureMIId=initTextureId(2);
        //初始化当前纹理id
        currTextureId=textureREId;
        //关闭背面剪裁
        GLES30.glDisable(GLES30.GL_CULL_FACE);
        MatrixHelper.setInitStack();
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
        //绘制当前纹理矩形
        texRect[trIndex].draw(currTextureId);
    }

    private int initTextureId(int repeatIndex) {
        int[] texures = new int[1];
        //生成纹理ID
        GLES30.glGenTextures(1, texures, 0);

        int texureId = texures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texureId);
        //MIN 采样方式
        GLES30.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        //MAG 采样方式
        GLES30.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        if (repeatIndex == 0)//如果索引值等于0
        {
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //S轴为重复拉伸方式
                    GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //T轴为重复拉伸方式
                    GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        } else if (repeatIndex == 1)//如果索引值等于1
        {
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //S轴为截取拉伸方式
                    GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //T轴为截取拉伸方式
                    GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        } else if (repeatIndex == 2)//如果索引值等于2
        {
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //S轴为镜像重复拉伸方式
                    GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_MIRRORED_REPEAT);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, //T轴为镜像重复拉伸方式
                    GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_MIRRORED_REPEAT);
        }
        Bitmap bitmap = BitmapUtils.BitmapFactory(this.getContext(), R.drawable.ic_launcher);
        GLUtils.texImage2D
                (
                        GLES30.GL_TEXTURE_2D,   //纹理类型
                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmap,              //纹理图像
                        0                      //纹理边框尺寸
                );
        bitmap.recycle();
        return texureId;
    }
}
