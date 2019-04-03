package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import pinetree.lifenavi.log.Log;
import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;
import pinetree.lifenavi.view.SixStarSurfaceView;

/**
 * Created by shisk on 2019/3/29.
 */

public class FiveStart {
    private final static String TAG = "FiveStart";
    //你需要一个程序来展示
    private int program;
    //拿到shader的定点
    private int vertexHandle;
    //h还需要片元的引用
    //颜色
    private int colorHandler;

    private int mvpHandler;
    //shander 源码
    private String vertexSource;
    private String fragmentSouce;

    private float[] mMatrix = new float[16];

    private float[] vertex = null;

    private FloatBuffer vertexFloatBuffer;
    private FloatBuffer colorFloatBuffer;
    private int vertexSize;
    private float yAngle;
    private float xAngle;

    public FiveStart(SixStarSurfaceView SixStarSurfaceView, float v, float v1, float v2) {
        initVertex(v, v1, v2);
        initShader(SixStarSurfaceView);
    }


    /**
     * @param innerR 六角形内圆
     * @param outR   六角形的外园
     * @param z      轴的距离
     */
    private void initVertex(float innerR, float outR, float z) {

        int tempAngle = 360 / 6;

        List<Float> cacheVertex = new ArrayList<>();

        for (int angle = 0; angle < 360; angle += tempAngle) {
            //第一个 三角形
            //第一个定点
            cacheVertex.add(0f);
            cacheVertex.add(0f);
            cacheVertex.add(z);
            //第二个顶点
            cacheVertex.add((float) (innerR *  Math.cos(Math.toRadians(angle))));
            cacheVertex.add((float) (innerR *  Math.sin(Math.toRadians(angle))));
            cacheVertex.add(z);
            //第三个顶点
            cacheVertex.add((float) (outR * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            cacheVertex.add((float) (outR * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            cacheVertex.add(z);

            //第2个三角形
            //第一个顶点
            cacheVertex.add(0f);
            cacheVertex.add(0f);
            cacheVertex.add(z);
            //第二个顶点
            cacheVertex.add((float) (outR * Math.cos(Math.toRadians(angle + tempAngle / 2))));
            cacheVertex.add((float) (outR * Math.sin(Math.toRadians(angle + tempAngle / 2))));
            cacheVertex.add(z);
            //第三个顶点
            cacheVertex.add((float) (innerR *  Math.cos(Math.toRadians(angle + tempAngle))));
            cacheVertex.add((float) (innerR *  Math.sin(Math.toRadians(angle + tempAngle))));
            cacheVertex.add(z);
        }
        vertexSize = cacheVertex.size() / 3;
        float[] vertexData = new float[cacheVertex.size()];

        for (int i = 0; i < cacheVertex.size(); i++) {
            vertexData[i] = cacheVertex.get(i);
        }
        vertexFloatBuffer = VBOHelper.getFloagBufferData(vertexData);

        //顶点着色数据的初始化================begin============================
        float[] colorArray = new float[vertexSize * 4];//顶点着色数据的初始化
        for (int i = 0; i < vertexSize; i++) {
            if (i % 3 == 0) {//中心点为白色，RGBA 4个通道[1,1,1,0]
                colorArray[i * 4] = 1;
                colorArray[i * 4 + 1] = 1;
                colorArray[i * 4 + 2] = 1;
                colorArray[i * 4 + 3] = 0;
            } else {//边上的点为淡蓝色，RGBA 4个通道[0.45,0.75,0.75,0]
                colorArray[i * 4] = 0.45f;
                colorArray[i * 4 + 1] = 0.75f;
                colorArray[i * 4 + 2] = 0.75f;
                colorArray[i * 4 + 3] = 0;
            }
        }
        colorFloatBuffer=VBOHelper.getFloagBufferData(colorArray);
    }

    private void initShader(GLSurfaceView gl) {
        vertexSource = ShaderUtil.loadFromAssetsFile("vertex.sh", gl.getResources());
        fragmentSouce = ShaderUtil.loadFromAssetsFile("frag.sh", gl.getResources());
        program = ShaderUtil.createProgram(vertexSource, fragmentSouce);

        //获取顶点着色器的引用
        vertexHandle = GLES30.glGetAttribLocation(program, "aPosition");
        //获取片元着色器的引用
        colorHandler = GLES30.glGetAttribLocation(program, "aColor");
        //获取最终变换矩阵
        mvpHandler = GLES30.glGetUniformLocation(program, "uMVPMatrix");
    }


    public void draw() {
        GLES30.glUseProgram(program);
        //初始化矩阵
        Matrix.setRotateM(mMatrix, 0, 0, 0, 1, 0);
        //沿Z轴位移1
        Matrix.translateM(mMatrix, 0, 0, 0, 1);
        //设置绕y轴旋转yAngle度
        Matrix.rotateM(mMatrix, 0, yAngle, 0, 1, 0);
        //设置绕x轴旋转xAngle度
        Matrix.rotateM(mMatrix, 0, xAngle, 1, 0, 0);
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(mvpHandler, 1, false, MatrixHelper.getFinalMatrix(mMatrix), 0);
        //将顶点数据传入渲染管线
        GLES30.glVertexAttribPointer(vertexHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexFloatBuffer);
        //将片元着色器传入渲染管线
        GLES30.glVertexAttribPointer(colorHandler, 3, GLES30.GL_FLOAT, false, 3 * 4, colorFloatBuffer);
        //启用顶点数据
        GLES30.glEnableVertexAttribArray(vertexHandle);
        //启用颜色数据
        GLES30.glEnableVertexAttribArray(colorHandler);
        //绘制六角形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexSize);
    }


}
