#version 300 es
uniform mat4 mvpMatrix;
in vec3  aPosition;
in vec2 texCoor;
out vec2 vTextCoor;
void main()
{
  gl_Position= mvpMatrix * vec4(aPosition,1);
  vTextCoor=texCoor;
}