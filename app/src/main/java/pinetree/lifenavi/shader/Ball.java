package pinetree.lifenavi.shader;

import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import pinetree.lifenavi.utils.Constant;
import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class Ball {
    private int program;
    private int mvpMatrix;
    private int vHandle;
    private int cHandle;
    private int uRHanlder;
    private FloatBuffer floatBuffer;

    private int vCount;

    public float yAngle;
    public float xAngle;
    public float zAngle;

    public void setyAngle(float yAngle) {
        this.yAngle = yAngle;
    }

    public void setxAngle(float xAngle) {
        this.xAngle = xAngle;
    }

    public void setzAngle(float zAngle) {
        this.zAngle = zAngle;
    }

    private float r = 0.8f;

    public Ball(){
        initShader();
        initVertexData();
    }

    private void initShader() {
        program = ShaderUtil.createProgram(Constant.vertex_ball, Constant.frag_ball);
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        vHandle = GLES30.glGetAttribLocation(program, "aPosition");
        uRHanlder = GLES30.glGetAttribLocation(program, "uR");
    }


    private void initVertexData() {
        List<Float> list = new ArrayList<>();
        final int angleSpan = 10;//将球进行单位切分的角度
        for (int vAangle = -90; vAangle < 90; vAangle += angleSpan) {
            for (int hAngle = 0; hAngle <= 360; hAngle += angleSpan) {
                float x0 = (float) (r * Math.cos(Math.toRadians(hAngle)) * Math.cos(Math.toRadians(vAangle)));
                float y0 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle)));
                float z0 = (float) (r * Math.sin(Math.toRadians(vAangle)));
                hAngle += angleSpan;
                float x1 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.cos(Math.toRadians(hAngle)));
                float y1 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle)));
                float z1 = (float) (r * Math.sin(Math.toRadians(vAangle)));
                vAangle += angleSpan;
                float x2 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.cos(Math.toRadians(hAngle)));
                float y2 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle)));
                float z2 = (float) (r * Math.sin(Math.toRadians(vAangle)));
                hAngle = hAngle - angleSpan;
                float x3 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.cos(Math.toRadians(hAngle)));
                float y3 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle)));
                float z3 = (float) (r * Math.sin(Math.toRadians(vAangle)));

                // 将计算出来的XYZ坐标加入存放顶点坐标的ArrayList
                list.add(x1);
                list.add(y1);
                list.add(z1);
                list.add(x3);
                list.add(y3);
                list.add(z3);
                list.add(x0);
                list.add(y0);
                list.add(z0);

                list.add(x1);
                list.add(y1);
                list.add(z1);
                list.add(x2);
                list.add(y2);
                list.add(z2);
                list.add(x3);
                list.add(y3);
                list.add(z3);
            }
        }
        vCount = list.size() / 3;
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < list.size(); i++) {
            vertices[i] = list.get(i);
        }
        floatBuffer = VBOHelper.getFloagBufferData(vertices);
    }

    public void draw() {
        MatrixHelper.roate(xAngle, 1, 0, 0);
        MatrixHelper.roate(yAngle, 0, 1, 0);
        MatrixHelper.roate(zAngle, 0, 0, 1);
        GLES30.glUseProgram(program);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glUniform1f(uRHanlder, r);
        GLES30.glVertexAttribPointer(vHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, floatBuffer);
        GLES30.glEnableVertexAttribArray(vHandle);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
