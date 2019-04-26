package pinetree.lifenavi.view;


import android.content.Context;
import android.opengl.GLES30;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.shader.Ball;
import pinetree.lifenavi.utils.MatrixHelper;

public class BallGLSurfaceView extends BaseGLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例
    Ball ball;//球

    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    private float lightOffset = -4;

    public BallGLSurfaceView(Context context) {
        super(context);
    }

    public BallGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDrawFrame(GL10 gl) {
        //清除深度缓冲与颜色缓冲
        GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

        MatrixHelper.setLightLocation(lightOffset, 0, 1.5f);

        //保护现场
        MatrixHelper.pushMatrix();
        //绘制球
        MatrixHelper.pushMatrix();
        MatrixHelper.translate(1.2f, 0, 0);
        ball.draw();
        MatrixHelper.popMatrix();

        //绘制球
        MatrixHelper.pushMatrix();
        MatrixHelper.translate(-1.2f, 0, 0);
        ball.draw();
        MatrixHelper.popMatrix();
        //恢复现场
        MatrixHelper.popMatrix();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视窗大小及位置
        GLES30.glViewport(0, 0, width, height);
        //计算GLSurfaceView的宽高比
        float ratio = (float) width / height;
        // 调用此方法计算产生透视投影矩阵
        MatrixHelper.setFrust(0, -ratio, ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixHelper.setLookAt(0, 0, 0, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        //初始化变换矩阵
        MatrixHelper.setInitStack();
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0f, 0f, 0f, 1.0f);
        //创建球对象
        ball = new Ball();
        //打开深度检测
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        //打开背面剪裁
        GLES30.glEnable(GLES30.GL_CULL_FACE);
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                ball.yAngle += dx * TOUCH_SCALE_FACTOR;//设置填充椭圆绕y轴旋转的角度
                ball.xAngle += dy * TOUCH_SCALE_FACTOR;//设置填充椭圆绕x轴旋转的角度
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    /**
     * 设置光照位置
     * @param v
     */
    public void setLightOffset(float v) {
        this.lightOffset = v;
    }
}
