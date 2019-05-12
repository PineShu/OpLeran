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


    public static final String  STAR_V="#version 300 es\n" +
            "uniform mat4 uMVPMatrix; //总变换矩阵\n" +
            "uniform float uPointSize;//点尺寸\n" +
            "in vec3 aPosition;  //顶点位置\n" +
            "\n" +
            "void main()     \n" +
            "{     //星空着色器的main方法\n" +
            "   //根据总变换矩阵计算此次绘制此顶点位置                         \t\t\n" +
            "   gl_Position = uMVPMatrix * vec4(aPosition,1); \n" +
            "   //\n" +
            "   gl_PointSize=uPointSize;\n" +
            "}              ";

    public static final String STAR_F="#version 300 es\n" +
            "precision mediump float;\n" +
            "out vec4 fragColor;\n" +
            "void main()\n" +
            "{\n" +
            "fragColor=vec4(1.0,1.0,1.0,1.0);\n" +
            "}";

    public static final String EARTH_VERTEX="#version 300 es\n" +
            "uniform mat4 mvpMatrix;\\总变换矩阵\n" +
            "uniform mat4 mMatrix;\\变换矩阵\n" +
            "in vec3 aPosition;\\ 顶点位置\n" +
            "uniform  vec3 lightLoc;\\光源位置\n" +
            "uniform  vec3 cameraLoc;\\相机位置\n" +
            "in vec2 textureCood;\\顶点的纹理坐标\n" +
            "in vec3 vNormal;\\法向量\n" +
            "out vec2  vTextureCoord;\n" +
            "out vec4 vAmbient;\n" +
            "out vec4 vDiffuse;\n" +
            "out vec4 vSpecular;\n" +
            "//球环境光 散射光 镜面光\n" +
            "void lightCaculate(in vec3 normal,inout vec4 ambient,inout vec4 diffuse,inout vec4 specular,in vec3 lightLocation,in vec4 lightAmbient,in vec4 lightDiffuse,in vec4 lightSpecular)\n" +
            "{\n" +
            "   //环境光  材质系数 * 环境光强度\n" +
            "   ambient=lightAmbient;  //直接得出环境光的强度\n" +
            "   //--satart--散射光  材质系数 * 散射光强度 * max(cos(入射角)，0);\n" +
            "   //入射角光源位置与法向量的夹角\n" +
            "   vec3 normalTarget=normal+aPosition;\n" +
            "   vec3 newNoarml=(mMatrix*vec4(normalTarget,1)).xyz-(mMatrix*vec4(aPosition,1)).xyz;    //计算变换后的法向量\n" +
            "   newNoarml=normalize(newNoarml);//规格化法向量\n" +
            "   vec3 lightV=normalize(lightLocation-(mMatrix*vec4(aPosition,1)).xyz);//表面点到光源位置的向量\n" +
            "   float cValue=max(0,dot(newNoarml,lightV));\n" +
            "   diffuse=lightDiffuse*cValue;\n" +
            "   //镜面光  材质系数 * 镜面光强度 * max(cos(半向角)，0)粗糙度;  添加粗糙度 就是对最大入射角 交粗糙度的平方，粗糙度越小 越光滑。\n" +
            "   //半向角= 光源向量+ 摄像头位置向量\n" +
            "   vec3 cameraVa=normalize(cameraLoc-(mMatrix*vec4(aPosition,1)).xyz);//从表面点到相机的向量\n" +
            "   vec3 halfValue=normalize(cameraVa+lightV);\n" +
            "   float shininess =50.0;//粗糙度，越小越光滑\n" +
            "   float cValueHalf=dot(newNoarml,halfValue);\n" +
            "   specular=lightSpecular*max(0.0,pow(cValueHalf,shininess))；\n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "\n" +
            " gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            "\n" +
            " vec4  abimetTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            " vec4  diffuseTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            " vec4  specularTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "\n" +
            " lightCaculate(normalize(vNormal),abimetTemp,diffuseTemp,specularTemp,vec4(0.05,0.05,0.05,1.0),vec4(1.0,1.0,1.0,1.0),vec4(0.3,0.3,0.3,1.0));\n" +
            "\n" +
            " vAmbient=abimetTemp;\n" +
            " vDiffuse=diffuseTemp;\n" +
            " vSpecular=specularTemp;\n" +
            "\n" +
            " vTextureCoord=textureCood;\n" +
            "\n" +
            "}";
    public static final String EARTH_FRAG="#version 300 es\n" +
            "precision mediump  float;\n" +
            "in vec2 vTextureCoord;\n" +
            "in vec4 vAmbient;\n" +
            "in vec4 vDiffuse;\n" +
            "in vec4 vSpecular;\n" +
            "\n" +
            "uniform sampler2D sTextureDay;\n" +
            "uniform sampler2D sTextureNight;\n" +
            "out vec4 fragColor;\n" +
            "void main()\n" +
            "{\n" +
            " vec4 finalDayColor;//从白天纹理中提取颜色\n" +
            " vec4 finalDayNight;//从夜晚纹理中提取到的颜色\n" +
            "\n" +
            " finalDayColor=texture(sTextureDay,vTextureCoord)*(vAmbient+vDiffuse+vSpecular);//采样出白天纹理的颜色值,计算出改片元白天的颜色值。\n" +
            " finalDayNight=texture(sTextureDay,vTextureCoord)**vec4(0.5,0.5,0.5,1.0);//采样出夜晚纹理的颜色值,计算出的该片元夜晚颜色值\n" +
            "\n" +
            " if(vDiffuse.x>0.21)\n" +
            "  {\n" +
            "    fragColor=finalDayColor;//白天颜色\n" +
            "  }else if (vDiffuse.x<0.05){\n" +
            "    fragColor=finalDayNight;//采用夜晚纹理\n" +
            "  }else{\n" +
            "    float t=(vDiffuse-0.05)/0.16;\n" +
            "    fragColor=t*finalDayColor+(1.0-t)*finalDayNight;\n" +
            "  }\n" +
            "\n" +
            "}";


    public static final String MOON_FRAG="#version 300 es\n" +
            "precision mediump float;\t//给出浮点默认精度\n" +
            "in vec2 texurCoord;//接收从顶点着色器过来的参数\n" +
            "in vec4 vAmbient;\n" +
            "in vec4 vDiffuse;\n" +
            "in vec4 vSpecular;\n" +
            "out vec4 fragColor;\n" +
            "uniform sampler2D sTexture;//纹理内容数据\n" +
            "void main()                         \n" +
            "{  //月球着色器的main方法\n" +
            "  //给此片元从纹理中采样出颜色值            \n" +
            "  vec4 finalColor = texture(sTexture, texurCoord); \n" +
            "  //给此片元颜色值 \n" +
            "  fragColor = finalColor*vAmbient+finalColor*vSpecular+finalColor*vDiffuse;\n" +
            "}          ";


    public static final String MOON_VERTEX="#version 300 es\n" +
            "uniform mat4 mvpMatrix;\n" +
            "uniform mat4 mmMatrex;\n" +
            "uniform vec3 uCamera;\n" +
            "uniform vec3 lightLocation;\n" +
            "in vec3 aPosition;\n" +
            "in vec2 aTexureCoord;\n" +
            "in vec3 aNormal;\n" +
            "out vec2 texurCoord;//传递给片元着色器\n" +
            "out vec4 vAmbient;\n" +
            "out vec4 vDiffuse;\n" +
            "out vec4 vSpecular;\n" +
            "//normal 法向量\n" +
            "//abiment 环境光 diffuse 散射光 specular 镜面光\n" +
            "//lightLoc 光照位置\n" +
            "//三种光照的强度\n" +
            "void  caculateLight(in vec3 normal,inout vec4 ambient,inout vec4 diffuse,inout vec4 specular,in vec3 lightLoc,\n" +
            "                in vec4 lightAbiment,in vec4 lightDiffuse,in vec4 lightSpecular)\n" +
            "{\n" +
            "  ambient=lightAbiment;//直接得出环境光信息，因为他是360度均匀散射的\n" +
            "  //散射光=散射光系数*散射光强度*max(cos(顶点法向量与光照位置的夹角，0))\n" +
            "  vec3 normalTarget=aPosition+normal;//计算变换后的法向量\n" +
            "  vec3 newNormal=(mmMatrex*vec4(normalTarget,1)).xyz-(mmMatrex*vec4(aPosition,1)).xyz;//求出指向球体的法向量\n" +
            "  newNormal=normalize(newNormal);//规格化法向量\n" +
            "  //相机规格化后的向量\n" +
            "  vec3 eye=normalize(uCamera-(mmMatrex*vec4(aPosition,1)).xyz);//指向球体的摄像头向量\n" +
            "  //光照位置规格化后的向量\n" +
            "  vec3 lightv=normalize(lightLoc-(mmMatrex*vec4(aPosition,1)).xyz);//指向球体的光照向量\n" +
            "  //光照与相机位置的半向量\n" +
            "  vec3 halfVector=normalize(eye+lightv);\n" +
            "\n" +
            "  float shinness=50.0;//粗糙度 越小越光滑\n" +
            "    //散射光=散射光系数*散射光强度*max(cos(顶点法向量与光照位置的夹角，0))\n" +
            "\n" +
            "    float mDot=max(0.0,dot(newNormal,lightv));//cos 光照与法向量\n" +
            "    diffuse=lightDiffuse*mDot;\n" +
            "    //镜面光=镜面光系数*镜面光强度*cos(half,normal)的粗糙度的平方。\n" +
            "    float dotSpecular=dot(newNormal,halfVector)\n" +
            "    float mDotSpe=max(0.0,pow(dotSpecular,shinness));//cos 光照与法向量\n" +
            "    specular=lightSpecular*mDotSpe\n" +
            "}\n" +
            "void main()\n" +
            "{\n" +
            "\n" +
            "   gl_Position = mvpMatrix * vec4(aPosition,1); //根据总变换矩阵计算此次绘制此顶点位置\n" +
            "\n" +
            "   vec4 ambientTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "   vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "   vec4 specularTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "\n" +
            "   caculateLight(normalize(aNormal),ambientTemp,diffuseTemp,specularTemp,uLightLocationSun,vec4(0.05,0.05,0.025,1.0),vec4(1.0,1.0,0.5,1.0),vec4(0.3,0.3,0.15,1.0));\n" +
            "\n" +
            "   vAmbient=ambientTemp;\n" +
            "   vDiffuse=diffuseTemp;\n" +
            "   vSpecular=specularTemp;\n" +
            "\n" +
            "   //将顶点的纹理坐标传给片元着色器\n" +
            "   texurCoord=aTexureCoord;\n" +
            "}\n" +
            "\n";
}