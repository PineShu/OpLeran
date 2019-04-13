package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class TextureTrangle {
    private int program;
    private int vertextHandler;
    private int vColorHandler;
    private int mvpMatrix;

    private FloatBuffer vertexBuffer;
    private FloatBuffer colorBuffer;

    private int vCount;

    public TextureTrangle(GLSurfaceView glSurfaceView) {
        init(glSurfaceView);
    }

    private void init(GLSurfaceView glSurfaceView) {

        String vertext = ShaderUtil.loadFromAssetsFile("t_vertex.sh", glSurfaceView.getResources());
        String frag = ShaderUtil.loadFromAssetsFile("r_frag.sh", glSurfaceView.getResources());
        program = ShaderUtil.createProgram(vertext, frag);
        vertextHandler = GLES30.glGetAttribLocation(program, "aPosition");
        vColorHandler = GLES30.glGetAttribLocation(program, "texCoor");
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        //顶点
        float vertex[] = new float[]{
                0, 0.9f, 0,
                -0.9f, -0.9f, 0,
                0.9f, -0.9f, 0
        };

        vCount = vertex.length / 3;

        vertexBuffer = VBOHelper.getFloagBufferData(vertex);
        float[] txtColor = new float[]{0.5f, 1, 0, 1, 1, 1};
        colorBuffer = VBOHelper.getFloagBufferData(txtColor);

    }

    public void draw(int texureId) {
        GLES30.glUseProgram(program);
        MatrixHelper.setInitStack();
        MatrixHelper.translate(0, 0, 1);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(vertextHandler, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);
        //纹理的坐标 多以传入的是2
        GLES30.glVertexAttribPointer(vColorHandler, 2, GLES30.GL_FLOAT, false, 2 * 4, colorBuffer);

        GLES30.glEnableVertexAttribArray(vertextHandler);
        GLES30.glEnableVertexAttribArray(vColorHandler);

        //绑定纹理数据
        GLES30.glActiveTexture(texureId);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texureId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
