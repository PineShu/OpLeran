package pinetree.lifenavi.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import pinetree.lifenavi.shader.Square;

/**
 * Created by shisk on 2019/3/20.
 */

public class SqureSurfaceView extends GLSurfaceView {
    private SqureRenderer renderer;

    private final String TAG = "SqureSurfaceView";

    public SqureSurfaceView(Context context) {
        super(context);
        init();
    }

    public SqureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        renderer = new SqureRenderer();
        setRenderer(renderer);
    }

    private final class SqureRenderer implements Renderer {
        Square square = null;

        //surface 创建的时候调用
        //在这个方法中主要用来设置一些绘制时不常变化的参数
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            square = new Square();

            // set the background color to black
            gl.glClearColor(0f, 0f, 0f, 0.5f);
            //Enable  Smooth shading,default not
            //原文翻译太复杂，我在这里总结一下：在使用顶点数据绘制几何图形时，如果为每个顶点指定了顶点颜色，
            // 此时若使用GL_SMOOTH，每个顶点使用对应的顶点颜色来着色，而顶点之间的片元颜色则使用差值的方式来计算获得，结果就是渐变色；
            // 而若使用GL_FLAT，假设几何图形由n个三角形构成，则只会使用顶点颜色数组中最后n个颜色进行着色。
            gl.glShadeModel(GL10.GL_SMOOTH);
            /**
             * opengl里面的深度缓存 
             在现实生活中，一个实心物体挡在另外一个实心物体的前面， 后面的那个物体有部分会被遮盖掉
             那么opengl里面如何模拟这个情况呢？ 每个物体的每个像素都有一个深度缓存的值（在0到1之间，可以想象成是z轴的距离）
             如果glDepthFunc启用了GL_LESS(现实生活中的前景）， 那么当前个物体挡住后个物体时，
             由于前个物体深度值小（越靠近人的）， 所以它就被画了出来， 后面的物体被挡住的像素就被忽略掉了。
             （当然你如果启用了GL_GREATER, 那么情况就反过来了）
             这个时候再来说glClearDepth, 它给深度缓冲指定了一个初始值，缓冲中的每个像素的深度值都是这个，
             比如1，这个时候你往里面画一个物体， 由于物体的每个像素的深度值都小于等于1， 所以整个物体都被显示了出来。
             如果初始值指定为0， 物体的每个像素的深度值都大于等于0， 所以整个物体都不可见。
             如果初始值指定为0.5， 那么物体就只有深度小于0.5的那部分才是可见的
             */
            // 1.0是最大深度([0.0,1.0])
            gl.glClearDepthf(1.0f);// OpenGL docs.
            // 启用深度测试
            gl.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
            // 所作深度测试的类型
            gl.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
            // 告诉系统对透视进行修正
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);


        }

        //如果设备支持屏幕横向和纵向切换，这个方法将发生在横向 <-> 纵向互换时。
        // 此时可以重新设置绘制的纵横比率。
        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged width=" + width + "-height=" + height);
            gl.glViewport(100, 100, width, height);
            //设置矩阵
            gl.glMatrixMode(GL10.GL_PROJECTION);
            // Reset the projection matrix
            gl.glLoadIdentity();// OpenGL docs.

            GLU.gluPerspective(gl, 45f, width / (float) height, 0.1f, 100f);
            Log.d(TAG,"width / (float) height="+width / (float) height);
            // Select the modelview matrix
            gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
            // Reset the modelview matrix
            gl.glLoadIdentity();// OpenGL docs.
        }

        //定义实际的绘图操作
        @Override
        public void onDrawFrame(GL10 gl) {
            // Clears the screen and depth buffer.
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT |
                    GL10.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity();
            gl.glTranslatef(0, 0, -4);
            // Draw our square.
            square.draw(gl); // ( NEW )
        }
    }
}
