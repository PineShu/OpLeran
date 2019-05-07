package pinetree.lifenavi.earth;

import java.nio.FloatBuffer;

import pinetree.lifenavi.utils.Constant;
import pinetree.lifenavi.utils.ShaderUtil;

public class Moon {
   private int program;
   private int mvpMatrix;
   private int mmMatrix;
   private int vertexHandler;  //顶点位置
   private int texureCoorditae;//纹理坐标
   private int ligthLocation;//光照位置
   private int cameraLocation;//相机位置
   private int normalHandler;//顶点法向量
   private FloatBuffer vertexBuffer;
   private FloatBuffer mTextureBuffer;


   private void initShader(){
       program= ShaderUtil.createProgram(Constant.Moo)
   }

}
