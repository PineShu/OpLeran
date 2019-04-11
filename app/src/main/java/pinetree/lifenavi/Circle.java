package pinetree.lifenavi;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class Circle {

    private int mvpMatrix;
    private int progrm;
    private int vertexHandle;
    private int vColorHandle;
    private int vCount;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    public Circle(GLSurfaceView glSurfaceView) {
        initShader(glSurfaceView);
        initVertex();
    }

    private void initShader(GLSurfaceView glSurfaceView) {
        String vertex = ShaderUtil.loadFromAssetsFile("circle_v.sh", glSurfaceView.getResources());
        String frag = ShaderUtil.loadFromAssetsFile("circle_f.sh", glSurfaceView.getResources());
        progrm = ShaderUtil.createProgram(vertex, frag);
        mvpMatrix = GLES30.glGetUniformLocation(progrm, "mMVPMatrix");
        vColorHandle = GLES30.glGetAttribLocation(progrm, "vColor");
        vertexHandle = GLES30.glGetAttribLocation(progrm, "vPosition");
    }

    private void initVertex() {
        //10段
        int n = 720;
        vCount = 2 * (n + 2);

        int bDgree = 0;
        int eDgree = 360;

        float span = (eDgree - bDgree) / (float)n;
        float[] veretexs = new float[vCount * 3];
        int vindex = 0;

        veretexs[vindex++] = 0;
        veretexs[vindex++] = 0;
        veretexs[vindex++] = 0;//z
;
        veretexs[vindex++] =0;
        veretexs[vindex++] = 0;
        veretexs[vindex++] = 0;//z

        for (float i = 0; i <=eDgree; i += span) {
            veretexs[vindex++] = (float) (-0.6f * Math.sin(Math.toRadians(i)));//x  三角函數公式  -sina=cos(pi/2+X)
            veretexs[vindex++] = (float) (0.6f * Math.cos(Math.toRadians(i)));//y  cos x=sina(pi/2+x)
            veretexs[vindex++] = 0;//z

            veretexs[vindex++] = (float) (-Math.sin(Math.toRadians(i)));//x  三角函數公式  -sina=cos(pi/2+X)
            veretexs[vindex++] = (float) (Math.cos(Math.toRadians(i)));//y  cos x=sina(pi/2+x)
            veretexs[vindex++] = 0;//z
        }
        vertexBuffer = VBOHelper.getFloagBufferData(veretexs);

        //顶点颜色值数组，每个顶点4个色彩值RGBA
        vindex = 0;

        float colors[] = new float[vCount * 4];
        colors[vindex++] = 1;
        colors[vindex++] = 1;
        colors[vindex++] = 1;
        colors[vindex++] = 0;

        colors[vindex++] = 1;
        colors[vindex++] = 1;
        colors[vindex++] = 1;
        colors[vindex++] = 0;
        for (int i = 8; i < colors.length; i += 8) {
            colors[vindex++] = 0;
            colors[vindex++] = 1;
            colors[vindex++] = 1;
            colors[vindex++] = 0;

            colors[vindex++] = 0;
            colors[vindex++] = 1;
            colors[vindex++] = 1;
            colors[vindex++] = 0;
        }

        colorBuffer = VBOHelper.getFloagBufferData(colors);
    }

    public void draw() {
        GLES30.glUseProgram(progrm);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(vertexHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES30.glVertexAttribPointer(vColorHandle, 4, GLES30.GL_FLOAT, false, 4 * 4, colorBuffer);

        GLES30.glEnableVertexAttribArray(vertexHandle);
        GLES30.glEnableVertexAttribArray(vColorHandle);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }


}
