package pinetree.lifenavi.utils;

public class Constant {

    public static final String vertex_ball = "#version  300 es \n" +
            "uniform  mat4  mvpMatrix;\n" +
            "in vec3 aPosition;\n" +
            "out vec3 vPosition;\n" +
            "out vec4 vAmbient;\n" +
            "void main()\n" +
            "{\n" +
            " gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            " vPosition=aPosition;\n" +
            " vAmbient=vec4(0.15,0.15,0.15,1.0);\n" +
            "}";
    public static final String frag_ball = "#version 300 es\n" +
            "precision  mediump   float;\n" +
            "in vec3 vPosition;//顶点坐标\n" +
            "uniform float uR;//球的半径\n" +
            "in vec4 vAmbient;//球的半径\n" +
            "out vec4  fragColor;//输出片元的颜色\n" +
            "void  main()\n" +
            "{\n" +
            "  vec3 color;\n" +
            "  float n=8.0;//球外接立方体每个坐标抽方向切分的份数\n" +
            "  float span=2.0*uR/n;//每一分立方体的边长\n" +
            "  int i=int ((vPosition.x+uR)/span);//当前片元位置小方块的行数\n" +
            "  int j=int ((vPosition.y+uR)/span);//当前位置小方块的层数\n" +
            "  int k=int ((vPosition.z+uR)/span);//当前位置小方块的列数\n" +
            "  //计算当前片元行数、层数、列数的和并对2取模\n" +
            "  int  colorType=int(mod(float(i+j+k),2.0));\n" +
            "  if(colorType == 1)\n" +
            "  {\n" +
            "  color = vec3(0.678,0.231,0.129);//红色\n" +
            "  }else{\n" +
            "  color = vec3(1.0,1.0,1.0);//白色\n" +
            "  }\n" +
            "  vec4 finalColor=vec4(color,0);\n" +
            "  fragColor=finalColor*vAmbient;\n" +
            "}\n";
    public static final  String BALL_DIFFUSE_VERTEX="#version 300 es\n" +
            "in vec3 aPosition;\n" +
            " uniform mat4 mvpMatrix;\n" +
            " uniform  mat4 mmMatrix;\n" +
            " uniform  vec3 cameraLocation;\n" +
            "out vec3  vPosition;\n" +
            "out vec4  vDiffuse;\n" +
            "uniform  vec3  lightLocation;\n" +
            "in  vec3  normallVec;\n" +
            "// nomal 法向量\n" +
            "//diffuse 存储散射光计算的结果\n" +
            "//lightLocation 光源位置\n" +
            "//lDiffuse 散射光强度\n" +
            "void pointLight(in vec3 normal,inout vec4 diffuse, in vec3 lightLocation,in vec4 lDiffuse)\n" +
            "{\n" +
            "\n" +
            "  vec3 nomalTarget=aPosition+normal;\n" +
            "  vec3 newNormal=(mmMatrix*vec4(nomalTarget,1)).xyz-(mmMatrix*vec4(aPosition,1)).xyz;//变换后的法向量\n" +
            "  newNormal=normalize(newNormal);//规格化法向量\n" +
            "  vec3 vp =normalize(lightLocation-(mmMatrix*vec4(aPosition,1)).xyz);//表面点到光源位置的向量。\n" +
            "  vec3 eye =normalize(cameraLocation-(mmMatrix*vec4(aPosition,1)).xyz);//表面点到光源位置的向量。\n" +
            "  vec3  vHalf=normalize(eye+vp);//法向量与入射向量的夹角余弦值。\n" +
            "  float  cvalue=dot(newNormal,vHalf);\n" +
            "  float  shininess=50.0;\n" +
            "  float  mDot=max(0.0,pow(cvalue,shininess));//粗糙度越小光照面积越大。\n" +
            "  diffuse=lDiffuse*mDot;//散射光最终强度 \n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            "vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "pointLight(normalize(normallVec),diffuseTemp,lightLocation,vec4(0.8,0.8,0.8,1.0));\n" +
            "vDiffuse=diffuseTemp;//最终散射光强度传递给片元着色器\n" +
            "vPosition=aPosition; \n" +
            "}";


    public static final String BALL_FRAG_DIFFUSE = "#version 300 es\n" +
            "precision  mediump   float;\n" +
            "in vec3 vPosition;//顶点坐标\n" +
            "uniform float uR;//球的半径\n" +
            "in vec4 vDiffuse;//球的半径\n" +
            "out vec4  fragColor;//输出片元的颜色\n" +
            "void  main()\n" +
            "{\n" +
            "  vec3 color;\n" +
            "  float n=8.0;//球外接立方体每个坐标抽方向切分的份数\n" +
            "  float span=2.0*uR/n;//每一分立方体的边长\n" +
            "  int i=int ((vPosition.x+uR)/span);//当前片元位置小方块的行数\n" +
            "  int j=int ((vPosition.y+uR)/span);//当前位置小方块的层数\n" +
            "  int k=int ((vPosition.z+uR)/span);//当前位置小方块的列数\n" +
            "  //计算当前片元行数、层数、列数的和并对2取模\n" +
            "  int  colorType=int(mod(float(i+j+k),2.0));\n" +
            "  if(colorType == 1)\n" +
            "  {\n" +
            "  color = vec3(0.678,0.231,0.129);//红色\n" +
            "  }else{\n" +
            "  color = vec3(1.0,1.0,1.0);//白色\n" +
            "  }\n" +
            "  vec4 finalColor=vec4(color,0);\n" +
            "  fragColor=finalColor*vDiffuse;\n" +
            "}\n";
}