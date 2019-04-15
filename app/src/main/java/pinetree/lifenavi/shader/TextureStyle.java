package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.ShaderUtil;

public class TextureStyle {

    private int progeram;
    private int mvpMatrix;
    private int vertexHandler;
    private int colorHandler;


    private FloatBuffer vBuffer;
    private FloatBuffer cBuffer;

    private void init(GLSurfaceView glSurfaceView) {
        String vertex = ShaderUtil.loadFromAssetsFile("tt_v.sh", glSurfaceView.getResources());
        String frag = ShaderUtil.loadFromAssetsFile("tt_frag.sh", glSurfaceView.getResources());
        progeram = ShaderUtil.createProgram(vertex, frag);
        mvpMatrix = GLES30.glGetUniformLocation(progeram, "mvpMatrix");
        vertexHandler = GLES30.glGetAttribLocation(progeram, "vPosition");
        colorHandler = GLES30.glGetAttribLocation(progeram, "aTextCoordiate");

        float[] vs=new float[]{

        };
    }

}
