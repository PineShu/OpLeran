#version 300 es
unifrom mat4 mvpMatrix;
in vec3  aPosition
in ve2 texCoor;
out vec2 vTextCoor;
void main()
{
  gl_Position= aPosition * ve4(aPosition,1);
  vTextCoor=texCoor;
}