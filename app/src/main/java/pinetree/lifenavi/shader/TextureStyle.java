package pinetree.lifenavi.shader;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;

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

        vBuffer = VBOHelper.getFloagBufferData(vs);

        float textCoord[] = new float[]{0, 0, 0, tRange, sRange, tRange, sRange, 0};

        cBuffer = VBOHelper.getFloagBufferData(textCoord);
    }

}
