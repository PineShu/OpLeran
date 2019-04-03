#version 300 es
uniform mat4 uMVPMatrix;//总变换矩阵
in vec3 vPosition;//顶点位置
in vec4 vColor;//顶点颜色
out vec4 toFColor;//传递给片元周色漆的颜色
void main()
{
   gl_Position=uMVPMatrix * vec4(vPosition,1);//根据总变换矩阵计算此次绘制此顶点位置
   toFColor=vColor;  //将接收的颜色传递给片元着色器
}