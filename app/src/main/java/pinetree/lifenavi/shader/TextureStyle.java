package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class TextureStyle {

    private int progeram;
    private int mvpMatrix;
    private int vertexHandler;
    private int colorHandler;


    private FloatBuffer vBuffer;
    private FloatBuffer cBuffer;

    private float tRange;
    private float sRange;
    private int vCount;

    public TextureStyle(float tRange, float sRange, GLSurfaceView glSurfaceView) {
        this.tRange = tRange;
        this.sRange = sRange;
        init(glSurfaceView);
    }

    private void init(GLSurfaceView glSurfaceView) {
        String vertex = ShaderUtil.loadFromAssetsFile("tt_v.sh", glSurfaceView.getResources());
        String frag = ShaderUtil.loadFromAssetsFile("tt_frag.sh", glSurfaceView.getResources());
        progeram = ShaderUtil.createProgram(vertex, frag);
        mvpMatrix = GLES30.glGetUniformLocation(progeram, "mvpMatrix");
        vertexHandler = GLES30.glGetAttribLocation(progeram, "vPosition");
        colorHandler = GLES30.glGetAttribLocation(progeram, "aTextCoordiate");

        final float U_SIZE = 0.3f;

        float[] vs = new float[]{
                -4 * U_SIZE, 4 * U_SIZE, 0,
                -4 * U_SIZE, -4 * U_SIZE, 0,
                4 * U_SIZE, -4 * U_SIZE, 0,
                4 * U_SIZE, 4 * U_SIZE, 0
        };

        float vertices[] = new float[]
                {
                        -4 * U_SIZE, 4 * U_SIZE, 0,
                        -4 * U_SIZE, -4 * U_SIZE, 0,
                        4 * U_SIZE, -4 * U_SIZE, 0,

                        4 * U_SIZE, -4 * U_SIZE, 0,
                        4 * U_SIZE, 4 * U_SIZE, 0,
                        -4 * U_SIZE, 4 * U_SIZE, 0
                };

        vBuffer = VBOHelper.getFloagBufferData(vertices);
        vCount = vertices.length / 3;
        float td[] = new float[]{0, 0, 0, tRange, sRange, tRange, sRange, 0};

        float texCoor[] = new float[]//顶点颜色值数组，每个顶点4个色彩值RGBA
                {
                        0, 0, 0, tRange, sRange, tRange,
                        sRange, tRange, sRange, 0, 0, 0
                };

        cBuffer = VBOHelper.getFloagBufferData(texCoor);
    }

    public void draw(int texureID) {

        GLES30.glUseProgram(progeram);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(vertexHandler, 3, GLES30.GL_FLOAT, false, 3 * 4, vBuffer);
        GLES30.glVertexAttribPointer(colorHandler, 4, GLES30.GL_FLOAT, false, 4 * 4, cBuffer);
        GLES30.glEnableVertexAttribArray(vertexHandler);
        GLES30.glEnableVertexAttribArray(colorHandler);

        GLES30.glActiveTexture(texureID);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texureID);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

}
