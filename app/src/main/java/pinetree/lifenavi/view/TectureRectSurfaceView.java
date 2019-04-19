package pinetree.lifenavi.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.R;
import pinetree.lifenavi.shader.TextureFilterRect;
import pinetree.lifenavi.utils.BitmapUtils;
import pinetree.lifenavi.utils.MatrixHelper;

public class TectureRectSurfaceView extends BaseGLSurfaceView {
    private TextureFilterRect filterRect;
    public int currenttexId32;                //大纹理矩形当前纹理的id
    public int currenttexId256;            //小纹理矩形当前纹理的id
    public int[] texId = new int[8];        //存储系统分配的纹理id的数组

    public TectureRectSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //创建纹理矩形对象
        filterRect = new TextureFilterRect(this);
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //初始化32*32像素的四个纹理
        texId[0] = initTexture(R.drawable.bw32, GLES30.GL_NEAREST, GLES30.GL_NEAREST);
        texId[1] = initTexture(R.drawable.bw32, GLES30.GL_LINEAR, GLES30.GL_LINEAR);
        texId[2] = initTexture(R.drawable.bw32, GLES30.GL_NEAREST, GLES30.GL_LINEAR);
        texId[3] = initTexture(R.drawable.bw32, GLES30.GL_LINEAR, GLES30.GL_NEAREST);
        //初始化256*256像素的四个纹理
        texId[4] = initTexture(R.drawable.bw256, GLES30.GL_NEAREST, GLES30.GL_NEAREST);
        texId[5] = initTexture(R.drawable.bw256, GLES30.GL_LINEAR, GLES30.GL_LINEAR);
        texId[6] = initTexture(R.drawable.bw256, GLES30.GL_NEAREST, GLES30.GL_LINEAR);
        texId[7] = initTexture(R.drawable.bw256, GLES30.GL_LINEAR, GLES30.GL_NEAREST);

        currenttexId32 = texId[0];    //设置当前纹理
        currenttexId256 = texId[4];    //设置当前纹理
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
        //初始化旋转矩阵
        MatrixHelper.setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
        //绘制小纹理矩形
        MatrixHelper.pushMatrix();    //保存当前的变换矩阵
        MatrixHelper.translate(0, 1.3f, 1);    //平移
        MatrixHelper.roate(-20, 0, 0, 1);    //旋转
        MatrixHelper.scale(0.3f, 0.3f, 0.3f); //缩放
        filterRect.drawSelf(currenttexId256);    //绘制小纹理矩形
        MatrixHelper.popMatrix();    //恢复当前的变换矩阵

        //绘制大纹理矩形
        MatrixHelper.pushMatrix();//保存当前的变换矩阵
        MatrixHelper.translate(0, -0.6f, 1);    //平移
        MatrixHelper.roate(-20, 0, 0, 1);    //旋转
        filterRect.drawSelf(currenttexId32);    //绘制大纹理矩形
        MatrixHelper.popMatrix();//恢复当前的变换矩阵
    }

    public int initTexture(int drawableId, float sample1, float sample2) {
        int[] textures = new int[1];//创建一个数组
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        int textureId = textures[0];    //纹理数组
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);//绑定纹理

        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, sample1);//确定纹理采样方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, sample2);//确定纹理采样方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        Bitmap bitmapTmp = BitmapUtils.BitmapFactory(getContext(), drawableId);
        //实际加载纹理
        GLUtils.texImage2D
                (
                        GLES30.GL_TEXTURE_2D,   //纹理类型
                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmapTmp,              //纹理图像
                        0                      //纹理边框尺寸
                );
        bitmapTmp.recycle();          //纹理加载成功后释放图片

        return textureId;
    }
}
