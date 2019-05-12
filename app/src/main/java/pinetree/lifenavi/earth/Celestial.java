package pinetree.lifenavi.earth;

import android.opengl.GLES10;
import android.opengl.GLES30;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

/**
 * 星星
 */
public class Celestial {

    private final String vertex = "#version 300 es\n" +
            "uniform mat4 mvpMatrix;\n" +
            "uniform float uPointSize;\n" +
            "in vec3 aPosition;\n" +
            "void main()\n" +
            "{\n" +
            " gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            " gl_PointSize=uPointSize;\n" +
            "}";
    private final String frag = "#version 300 es\n" +
            "precision mediump  float;\n" +
            "out vec4 fragColor;\n" +
            "void main()\n" +
            "{\n" +
            " fragColor=vec4(1.0,1.0,1.0,1.0);\n" +
            "}\n";
    private int program;
    private int mvpMatrix;
    private int position;
    private int pointSize;
    private final float R = 10.0f;
    //星星的数量
    private int count;
    //星星的尺寸
    private float scale;

    private FloatBuffer vertexBuffer;

    public Celestial(int scale, float count) {
        this.count = (int) count;
        this.scale = scale;
        initShader();
        initVertext();
    }

    private void initShader() {
        program = ShaderUtil.createProgram(vertex, frag);
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        position = GLES30.glGetAttribLocation(program, "aPosition");
        pointSize = GLES30.glGetUniformLocation(program, "uPointSize");
    }

    private void initVertext() {
        float vertexs[] = new float[count * 3];
        for (int i = 0; i < count; i++) {
            double lon = Math.PI * 2 * Math.random();//弧度制的 2PI=360
            double lat = Math.PI * (Math.random()-0.5f);//弧度制 PI=180
            vertexs[i * 3] = (float) (R * Math.cos(lat) * Math.sin(lon));
            vertexs[i * 3 + 1] = (float) (R * Math.sin(lat));
            vertexs[i * 3 + 2] = (float) (R * Math.cos(lat) * Math.cos(lon));
        }
        vertexBuffer = VBOHelper.getFloagBufferData(vertexs);
    }

    public void draw() {
        GLES30.glUseProgram(program);
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        GLES30.glUniform1f(pointSize, scale);
        GLES30.glVertexAttribPointer(position, 3, GLES30.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES30.glEnableVertexAttribArray(position);
        /**
         * GL_POINTS
         * 点精灵图元，对指定的每个顶点进行绘制。
         * GL_LINES
         * 绘制一系列不相连的线段。
         * GL_LINE_STRIP
         * 绘制一系列相连的线段。
         * GL_LINE_LOOP
         * 绘制一系列相连的线段，首尾相连。
         * GL_TRIANGLES
         * 绘制一系列单独的三角形。
         * GL_TRIANGLE_STRIP
         * 绘制一系列相互连接的三角形。
         * GL_TRIANGLE_FAN
         * 绘制一系列相互连接的三角形。
         */
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, count);
    }

}
