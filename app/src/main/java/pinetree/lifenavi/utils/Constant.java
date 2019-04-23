package pinetree.lifenavi.utils;

public class Constant {

    public static final String vertex_ball = "#version  300 es \n" +
            "uniform  mat4  mvpMatrix;\n" +
            "in vec3 aPosition;\n" +
            "out vec3 vPosition;\n" +
            "void main()\n" +
            "{\n" +
            " gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            " vPosition=aPosition;\n" +
            "}";
    public static final String frag_ball = "#version 300 es\n" +
            "precision  mediump   float;\n" +
            "in vec3 vPosition;//顶点坐标\n" +
            "uniform float uR;//球的半径\n" +
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
            "  fragColor=vec4(color,0);\n" +
            "}\n";

}
