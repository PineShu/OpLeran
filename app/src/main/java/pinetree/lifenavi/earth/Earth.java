package pinetree.lifenavi.earth;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import pinetree.lifenavi.utils.Constant;
import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class Earth {
    private int program;
    private int mvpMatrix;
    private int mMatrix;
    private int aPosition;
    private int normalVec;
    private int cameraLoc;
    private int lightLoc;
    private int textureCoordiate;//顶点纹理坐标
    private int dayTexure;
    private int nightTexure;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexcoorBuffer;
    private int vCount = 0;


    private void initShader() {
        program = ShaderUtil.createProgram(Constant.EARTH_VERTEX, Constant.EARTH_FRAG);
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        mMatrix = GLES30.glGetUniformLocation(program, "mMatrix");
        lightLoc = GLES30.glGetUniformLocation(program, "lightLoc");
        cameraLoc = GLES30.glGetUniformLocation(program, "cameraLoc");
        textureCoordiate = GLES30.glGetAttribLocation(program, "textureCood");
        normalVec = GLES30.glGetAttribLocation(program, "vNormal");
        aPosition = GLES30.glGetAttribLocation(program, "aPosition");
    }


    private void drawSelf(int texID,int nightId) {
        GLES30.glUseProgram(program);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glUniformMatrix4fv(mMatrix, 1, false, MatrixHelper.getMMatrix(), 0);
        GLES30.glUniform3fv(cameraLoc, 1, MatrixHelper.getCameraLocationBuffer());
        GLES30.glUniform3fv(lightLoc,1,MatrixHelper.getLightLocation());
        GLES30.glVertexAttribPointer(aPosition,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES30.glVertexAttribPointer(normalVec,3,GLES30.GL_FLOAT,false,3*4,mVertexBuffer);
        GLES30.glVertexAttribPointer(textureCoordiate,2,GLES30.GL_FLOAT,false,2*4,mTexcoorBuffer);
        GLES30.glEnableVertexAttribArray(aPosition);
        GLES30.glEnableVertexAttribArray(normalVec);
        GLES30.glEnableVertexAttribArray(textureCoordiate);

        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES20.GL_TEXTURE_2D,texID);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES20.GL_TEXTURE_2D,nightId);

        GLES30.glUniform1i(dayTexure, 0);//通过引用指定白天纹理
        GLES30.glUniform1i(nightTexure, 1);  //通过引用指定黑夜纹理
        //以三角形方式执行绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

    //初始化顶点数据的方法
    public void initVertexData(float r) {
        //顶点坐标数据的初始化================begin============================
        final float UNIT_SIZE = 0.5f;
        ArrayList<Float> alVertix = new ArrayList<Float>();//存放顶点坐标的ArrayList
        final float angleSpan = 10f;//将球进行单位切分的角度
        for (float vAngle = 90; vAngle > -90; vAngle = vAngle - angleSpan) {//垂直方向angleSpan度一份
            for (float hAngle = 360; hAngle > 0; hAngle = hAngle - angleSpan) {//水平方向angleSpan度一份
                //纵向横向各到一个角度后计算对应的此点在球面上的坐标
                double xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                float x1 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z1 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y1 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));
                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x2 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z2 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y2 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));
                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x3 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z3 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y3 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));
                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                float x4 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z4 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y4 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));
                //构建第一三角形
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                //构建第二三角形
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
            }
        }
        vCount = alVertix.size() / 3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标
        //将alVertix中的坐标值转存到一个float数组中
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < alVertix.size(); i++) {
            vertices[i] = alVertix.get(i);
        }
        mVertexBuffer = VBOHelper.getFloagBufferData(vertices);
        //将alTexCoor中的纹理坐标值转存到一个float数组中
        float[] texCoor = generateTexCoor(//获取切分整图的纹理数组
                (int) (360 / angleSpan), //纹理图切分的列数
                (int) (180 / angleSpan)  //纹理图切分的行数
        );
        mTexcoorBuffer = VBOHelper.getFloagBufferData(texCoor);
    }

    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw, int bh) {
        float[] result = new float[bw * bh * 6 * 2];
        float sizew = 1.0f / bw;//列数
        float sizeh = 1.0f / bh;//行数
        int c = 0;
        for (int i = 0; i < bh; i++) {
            for (int j = 0; j < bw; j++) {
                //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
                float s = j * sizew;
                float t = i * sizeh;//得到i行j列小矩形的左上点的纹理坐标值
                result[c++] = s;
                result[c++] = t;//该矩形左上点纹理坐标值
                result[c++] = s;
                result[c++] = t + sizeh;//该矩形左下点纹理坐标值
                result[c++] = s + sizew;
                result[c++] = t;            //该矩形右上点纹理坐标值
                result[c++] = s + sizew;
                result[c++] = t;//该矩形右上点纹理坐标值
                result[c++] = s;
                result[c++] = t + sizeh;//该矩形左下点纹理坐标值
                result[c++] = s + sizew;
                result[c++] = t + sizeh;    //该矩形右下点纹理坐标值
            }
        }
        return result;

    }
}
