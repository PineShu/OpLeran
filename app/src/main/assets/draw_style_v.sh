#version  300 es
uniform mat4 uMvpMatrix;
in vec3  vPosition;
in vec4  vColor;
out vec4 fColor;
void main()
{
  gl_Position= uMvpMatrix * vec4(vPosition,1);
  gl_PointSize=10.0;
  fColor=vColor;
}