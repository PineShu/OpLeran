package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

/**
 * Created by shisk on 2019/4/3.
 */

public class Cube {

    private static final int U_SIZE = 1;
    private int program;
    private FloatBuffer vertexFloatBuffer;
    private FloatBuffer colorFloatBuffer;
    private int mvpMatrix;
    private int vPosition;
    private int vColor;
    private int vConut;


    public Cube(GLSurfaceView glSurfaceView) {
        initVertex();
        intShader(glSurfaceView);
    }

    private void initVertex() {

        float[] floats = new float[]{
                //front
                0, 0, U_SIZE, U_SIZE, U_SIZE, U_SIZE, -U_SIZE, U_SIZE, U_SIZE,
                0, 0, U_SIZE, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE,
                0, 0, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE, U_SIZE,
                0, 0, U_SIZE, U_SIZE, -U_SIZE, U_SIZE, U_SIZE, U_SIZE, U_SIZE,
                //back
                0, 0, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE,
                0, 0, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE,
                0, 0, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE,
                0, 0, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE,
                //left
                -U_SIZE, 0, 0, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE, U_SIZE, -U_SIZE,
                -U_SIZE, 0, 0, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE,
                -U_SIZE, 0, 0, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE, U_SIZE,
                -U_SIZE, 0, 0, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, U_SIZE, U_SIZE,
                //right
                U_SIZE, 0, 0, U_SIZE, U_SIZE, -U_SIZE, U_SIZE, U_SIZE, U_SIZE,
                U_SIZE, 0, 0, U_SIZE, U_SIZE, U_SIZE, U_SIZE, -U_SIZE, U_SIZE,
                U_SIZE, 0, 0, U_SIZE, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE, -U_SIZE,
                U_SIZE, 0, 0, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, U_SIZE, -U_SIZE,
                //top
                0, U_SIZE, 0, U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE,
                0, U_SIZE, 0, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, U_SIZE,
                0, U_SIZE, 0, -U_SIZE, U_SIZE, U_SIZE, U_SIZE, U_SIZE, U_SIZE,
                0, U_SIZE, 0, U_SIZE, U_SIZE, U_SIZE, U_SIZE, U_SIZE, -U_SIZE,
                //bottom
                0, -U_SIZE, 0, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, U_SIZE,
                0, -U_SIZE, 0, U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, U_SIZE,
                0, -U_SIZE, 0, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE, -U_SIZE,
                0, -U_SIZE, 0, -U_SIZE, -U_SIZE, -U_SIZE, U_SIZE, -U_SIZE, -U_SIZE

        };
        vConut = floats.length / 3;
        //顶点颜色值数组，每个顶点4个色彩值RGBA
        float colors[] = new float[]{
                //前面
                1, 1, 1, 0,//中间为白色
                1, 0, 0, 0,
                1, 0, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 0, 0,
                1, 0, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 0, 0,
                1, 0, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 0, 0,
                1, 0, 0, 0,
                //后面
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 0, 1, 0,
                0, 0, 1, 0,
                //左面
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,//中间为白色
                1, 0, 1, 0,
                1, 0, 1, 0,
                //右面
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                1, 1, 0, 0,
                1, 1, 0, 0,
                //上面
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 0, 0,
                0, 1, 0, 0,
                //下面
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
                1, 1, 1, 0,//中间为白色
                0, 1, 1, 0,
                0, 1, 1, 0,
        };

        vertexFloatBuffer = VBOHelper.getFloagBufferData(floats);
        colorFloatBuffer = VBOHelper.getFloagBufferData(colors);

    }

    private void intShader(GLSurfaceView glSurfaceView) {
        String fragment = ShaderUtil.loadFromAssetsFile("cube_frag.sh", glSurfaceView.getResources());
        String vertex = ShaderUtil.loadFromAssetsFile("cube_vertex.sh", glSurfaceView.getResources());

        program = ShaderUtil.createProgram(vertex, fragment);

        vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        vColor = GLES30.glGetAttribLocation(program, "vColor");
        mvpMatrix = GLES30.glGetUniformLocation(program, "uMVPMatrix");

    }

    public void draw() {
        GLES30.glUseProgram(program);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glVertexAttribPointer(vPosition, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexFloatBuffer);
        GLES30.glVertexAttribPointer(vColor, 4, GLES30.GL_FLOAT, false, 4 * 4, colorFloatBuffer);

        GLES30.glEnableVertexAttribArray(vPosition);
        GLES30.glEnableVertexAttribArray(vColor);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vConut);
    }

}
