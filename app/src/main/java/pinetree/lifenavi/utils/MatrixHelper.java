package pinetree.lifenavi.utils;

import android.opengl.Matrix;

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

}
