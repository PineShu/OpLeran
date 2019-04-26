package pinetree.lifenavi.shader;

import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
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
    private FloatBuffer normalBuffer;


    private int uMatrix;
    private int lightLocation;
    private int normalVec;
    private int cameraLoc;
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

    public Ball() {
        initShader();
        initVertexData();
    }

    private void initShader() {
        program = ShaderUtil.createProgram(Constant.BALL_DIFFUSE_VERTEX, Constant.BALL_FRAG_DIFFUSE);
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        vHandle = GLES30.glGetAttribLocation(program, "aPosition");
        uRHanlder = GLES30.glGetUniformLocation(program, "uR");
        normalVec = GLES30.glGetAttribLocation(program, "normallVec");
        uMatrix = GLES30.glGetUniformLocation(program, "mmMatrix");
        lightLocation = GLES30.glGetUniformLocation(program, "lightLocation");
        cameraLoc = GLES30.glGetUniformLocation(program, "cameraLocation");
    }


    private void initVertexData() {
        List<Float> list = new ArrayList<>();
        final int angleSpan = 10;//将球进行单位切分的角度
        for (int vAangle = -90; vAangle < 90; vAangle += angleSpan) {
            for (int hAngle = 0; hAngle <= 360; hAngle += angleSpan) {
                float x0 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.cos(Math.toRadians(hAngle)));
                float y0 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle)));
                float z0 = (float) (r * Math.sin(Math.toRadians(vAangle)));

                float x1 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y1 = (float) (r * Math.cos(Math.toRadians(vAangle)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z1 = (float) (r * Math.sin(Math.toRadians(vAangle)));

                float x2 = (float) (r * Math.cos(Math.toRadians(vAangle + angleSpan)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float) (r * Math.cos(Math.toRadians(vAangle + angleSpan)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float) (r * Math.sin(Math.toRadians(vAangle + angleSpan)));

                float x3 = (float) (r * Math.cos(Math.toRadians(vAangle + angleSpan)) * Math.cos(Math.toRadians(hAngle)));
                float y3 = (float) (r * Math.cos(Math.toRadians(vAangle + angleSpan)) * Math.sin(Math.toRadians(hAngle)));
                float z3 = (float) (r * Math.sin(Math.toRadians(vAangle + angleSpan)));


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
        normalBuffer = VBOHelper.getFloagBufferData(Arrays.copyOf(vertices, vertices.length));
    }

    public void draw() {
        MatrixHelper.roate(xAngle, 1, 0, 0);
        MatrixHelper.roate(yAngle, 0, 1, 0);
        MatrixHelper.roate(zAngle, 0, 0, 1);
        GLES30.glUseProgram(program);
        GLES30.glUniform3fv(lightLocation,1,MatrixHelper.getLightLocation());
        GLES30.glUniform3fv(cameraLoc,1,MatrixHelper.getCameraLocationBuffer());
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glUniformMatrix4fv(uMatrix, 1, false, MatrixHelper.getMMatrix(), 0);
        GLES30.glUniform1f(uRHanlder, r);
        GLES30.glVertexAttribPointer(vHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, floatBuffer);
        GLES30.glVertexAttribPointer(normalVec, 3, GLES30.GL_FLOAT, false, 3 * 4, normalBuffer);
        GLES30.glEnableVertexAttribArray(vHandle);
        GLES30.glEnableVertexAttribArray(normalVec);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
