package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class TextureFilterRect {

    private int program;
    private int vertexHandle;
    private int colorHandle;
    private int mvpMatrix;
    private FloatBuffer vBuffer;
    private FloatBuffer cBuffer;
    private int vCount;

    public TextureFilterRect(GLSurfaceView glSurfaceView) {
        initShader(glSurfaceView);
        initVertex();
    }

    private void initShader(GLSurfaceView glSurfaceView) {
        String v = ShaderUtil.loadFromAssetsFile("texture_fileter_v.sh", glSurfaceView.getContext().getResources());
        String f = ShaderUtil.loadFromAssetsFile("texture_fileter_f.sh", glSurfaceView.getContext().getResources());
        program = ShaderUtil.createProgram(v, f);
        vertexHandle = GLES30.glGetAttribLocation(program, "aPosition");
        colorHandle = GLES30.glGetAttribLocation(program, "texureCoord");
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
    }

    private void initVertex() {
//顶点坐标数据的初始化================begin============================
        vCount = 6;
        final float UNIT_SIZE = 0.15f;
        float vertices[] = new float[]
                {
                        //较大的纹理矩形
                        -6 * UNIT_SIZE, 6 * UNIT_SIZE, 0,
                        -6 * UNIT_SIZE, -6 * UNIT_SIZE, 0,
                        6 * UNIT_SIZE, -6 * UNIT_SIZE, 0,

                        6 * UNIT_SIZE, -6 * UNIT_SIZE, 0,
                        6 * UNIT_SIZE, 6 * UNIT_SIZE, 0,
                        -6 * UNIT_SIZE, 6 * UNIT_SIZE, 0
                };
        vBuffer = VBOHelper.getFloagBufferData(vertices);
        //顶点纹理坐标数据的初始化================begin============================
        float texCoor[] = new float[]//顶点颜色值数组，每个顶点4个色彩值RGBA
                {
                        0, 0, 0, 1, 1, 1,
                        1, 1, 1, 0, 0, 0
                };
        cBuffer = VBOHelper.getFloagBufferData(texCoor);
    }

    public void drawSelf(int texId) {
        //指定使用某套shader程序
        GLES30.glUseProgram(program);


        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (vertexHandle
                        ,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        vBuffer
                );
        //将顶点纹理数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        colorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2 * 4,
                        cBuffer
                );
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(vertexHandle);
        //启用顶点纹理数据数组
        GLES30.glEnableVertexAttribArray(colorHandle);

        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);

        //绘制图形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

}
