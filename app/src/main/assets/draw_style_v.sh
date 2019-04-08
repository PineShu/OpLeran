#version  es 300;
uniform mat4 uMvpMatrix;
in vec3  vPosition;
in vec4  vColor;
out vec4 fColor;
void main()
{
  gl_Position= uMvpMatrix * (vPosition,1);
  gl_PointSize=10.0;
  fColor=vColor;
}