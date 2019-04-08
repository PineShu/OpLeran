package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class DrawStyle {

    private int program;
    private int vertexHandle;
    private int colorHandle;
    private int mvpMatrix;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    public DrawStyle(GLSurfaceView glSurfaceView) {
        initShader(glSurfaceView);
        initVertexData();
    }

    private void initShader(GLSurfaceView glSurfaceView) {
        String vStr = ShaderUtil.loadFromAssetsFile("draw_style_v.sh", glSurfaceView.getResources());
        String fStr = ShaderUtil.loadFromAssetsFile("draw_style_fg.sh", glSurfaceView.getResources());

        program = ShaderUtil.createProgram(vStr, fStr);

        mvpMatrix = GLES30.glGetUniformLocation(program, "uMvpMatrix");
        vertexHandle = GLES30.glGetAttribLocation(program, "vPosition");
        colorHandle = GLES30.glGetAttribLocation(program, "vColor");

    }


    private void initVertexData() {
        float vertices[] = new float[]{
                0, 0, 0,
                1, 1, 0,
                -1, 1, 0,
                -1, -1, 0,
                1, -1, 0};

        vertexBuffer = VBOHelper.getFloagBufferData(vertices);
        // 顶点颜色值数组，每个顶点4个色彩值RGBA
        float colors[] = new float[]{
                1, 1, 0, 0,// 黄
                1, 1, 1, 0,// 白
                0, 1, 0, 0,// 绿
                1, 1, 1, 0,// 白
                1, 1, 0, 0,// 黄
        };
        colorBuffer = VBOHelper.getFloagBufferData(colors);
    }


    public void draw(int style) {
        GLES30.glUseProgram(program);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(vertexHandle, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES30.glVertexAttribPointer(colorHandle, 4, GLES30.GL_FLOAT, false, 4 * 4, colorBuffer);
        GLES30.glEnableVertexAttribArray(vertexHandle);
        GLES30.glEnableVertexAttribArray(colorHandle);
        GLES30.glLineWidth(10);
        GLES30.glDrawArrays(style, 0, 5);
    }

}
