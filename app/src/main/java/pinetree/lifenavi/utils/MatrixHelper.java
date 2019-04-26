package pinetree.lifenavi.utils;

import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by shisk on 2019/3/29.
 */

public class MatrixHelper {
    //存储生成矩阵元素的float[]型数组  摄像机矩阵
    private static float[] mVMatrix = new float[16];
    //存储生成矩阵元素的loath类型的数组  投影矩阵
    private static float[] mProjMatrix = new float[16];
    //最终的变换矩阵
    private static float[] mMvpMatrix;
    private static float[] currMatrix;//当前变换矩阵


    private static Stack<float[]> queue = new Stack();


    private static FloatBuffer locationBuffer;
    private static float[] lightLocation = new float[3];


    //将当前变换矩阵存入栈中
    public static void pushMatrix() {
        queue.add(Arrays.copyOf(currMatrix, currMatrix.length));
    }

    //从栈顶取出变换矩阵
    public static void popMatrix() {
        currMatrix = queue.pop();
    }

    /**
     * 设置相近的 方向 up  位置
     *
     * @param rmOffset 起始偏移量
     * @param eyeX     相机位置
     * @param eyeY
     * @param eyeZ
     * @param centerX  观察方向的位置
     * @param centerY
     * @param centerZ
     * @param upX      up向量的坐标
     * @param upY
     * @param upZ
     */
    public static void setLookAt(int rmOffset, float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        Matrix.setLookAtM(mVMatrix, rmOffset, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    /**
     * 设置正交投影 视点为相机的位置 距离视点近垂直于观察方向的称为近平面，距离视点较远垂直于观察方向的远平面far
     * 垂直于观察方向的 left right bottom top 确定了4个平面的视镜体。
     *
     * @param orOffest 偏移量
     * @param left     近平面对应的x坐标
     * @param right    近平面对应的x坐标
     * @param bottom   近平面对应的Y坐标
     * @param top      近平面对应的Y坐标
     * @param near     视景体近平面与视点的距离
     * @param far      视镜体远平面与视点的距离
     */
    public static void setOrthoM(int orOffest, float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mProjMatrix, orOffest, left, right, bottom, top, near, far);
    }

    /**
     * 透视投影
     *
     * @param orOffest
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public static void setFrust(int orOffest, float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, orOffest, left, right, bottom, top, near, far);
    }

    //沿X、Y、Z轴方向进行平移变换的方法
    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    //沿X、Y、Z轴方向进行旋转
    public static void roate(float angle, float x, float y, float z) {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }


    /**
     * 获取最终的变换矩阵
     *
     * @param spec
     * @return
     */
    public static float[] getFinalMatrix(float[] spec) {
        mMvpMatrix = new float[16];
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMvpMatrix, 0, mVMatrix, 0, spec, 0);
        //投影矩阵乘以上一步的结果
        Matrix.multiplyMM(mMvpMatrix, 0, mProjMatrix, 0, mMvpMatrix, 0);
        return mMvpMatrix;
    }


    //获取具体物体的总变换矩阵
    static float[] mMVPMatrix = new float[16];//总变换矩阵

    public static float[] getFinalMatrix()//计算产生总变换矩阵的方法
    {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        //投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }


    //产生无任何变换的初始矩阵
    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    //获取具体物体的变换矩阵
    public static float[] getMMatrix() {
        return currMatrix;
    }

    /**
     * @param x
     * @param y
     * @param z
     */
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix, 0, x, y, z);
    }

    /**
     * 获取光照位置FloatBuffer
     *
     * @return
     */
    public static FloatBuffer getLightLocation() {
        return VBOHelper.getFloagBufferData(lightLocation);
    }

    /**
     * 设置光照位置
     *
     * @param x
     * @param y
     * @param z
     */
    public static void setLightLocation(float x, float y, float z) {
        lightLocation[0] = x;
        lightLocation[1] = y;
        lightLocation[2] = z;
    }
}
