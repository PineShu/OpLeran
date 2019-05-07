#version 300 es
uniform mat4 mvpMatrix;
uniform mat4 mmMatrex;
uniform vec3 uCamera;
uniform vec3 lightLocation;
in vec3 aPosition;
in vec2 aTexureCoord;
in vec3 aNormal;
out vec2 texurCoord;//传递给片元着色器
out vec4 vAmbient;
out vec4 vDiffuse;
out vec4 vSpecular;
//normal 法向量
//abiment 环境光 diffuse 散射光 specular 镜面光
//lightLoc 光照位置
//三种光照的强度
void  caculateLight(vec3 normal,inout vec4 ambient,inout vec4 diffuse,inout vec4 specular,in vec3 lightLoc,
                in vec4 lightAbiment,in vec4 lightDiffuse,in vec4 lightSpecular)
{
  ambient=lightAbiment;//直接得出环境光信息，因为他是360度均匀散射的
  //散射光=散射光系数*散射光强度*max(cos(顶点法向量与光照位置的夹角，0))
  vec3 normalTarget=aPosition+normal;//计算变换后的法向量
  vec3 newNormal=(mmMatrex*vec4(normalTarget,1)).xyz-(mmMatrex*vec4(aPosition,1)).xyz;//求出指向球体的法向量
  newNormal=normalize(newNormal);//规格化法向量
  //相机规格化后的向量
  vec3 eye=normalize(uCamera-(mmMatrex*vec4(aPosition,1)).xyz);//指向球体的摄像头向量
  //光照位置规格化后的向量
  vec3 lightLoc=normalize(lightLoc-(mmMatrex*vec4(aPosition,1)).xyz);//指向球体的光照向量
  //光照与相机位置的半向量
  vec3 halfVector=normalize(eye+lightLoc);

  float shinness=50;//粗糙度 越小越光滑
    //散射光=散射光系数*散射光强度*max(cos(顶点法向量与光照位置的夹角，0))

    float mDot=max(0.0,dot(newNormal,lightLoc));//cos 光照与法向量
    diffuse=lightDiffuse*mDot;
    //镜面光=镜面光系数*镜面光强度*cos(half,normal)的粗糙度的平方。
    float dotSpecular=dot(newNormal,halfVector)
    float mDotSpe=max(0.0,pow(dotSpecular,shinness));//cos 光照与法向量
    specular=lightSpecular*mDotSpe
}
void main()
{

   gl_Position = mvpMatrix * vec4(aPosition,1); //根据总变换矩阵计算此次绘制此顶点位置

   vec4 ambientTemp=vec4(0.0,0.0,0.0,0.0);
   vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);
   vec4 specularTemp=vec4(0.0,0.0,0.0,0.0);

   caculateLight(normalize(aNormal),ambientTemp,diffuseTemp,specularTemp,uLightLocationSun,vec4(0.05,0.05,0.025,1.0),vec4(1.0,1.0,0.5,1.0),vec4(0.3,0.3,0.15,1.0));

   vAmbient=ambientTemp;
   vDiffuse=diffuseTemp;
   vSpecular=specularTemp;

   //将顶点的纹理坐标传给片元着色器
   texurCoord=aTexureCoord;
}