#version 300 es
uniform  mat4  mvpMatrix;
in vec3 vPosition;
in vec2 aTextCoordiate;
out vec2 vTextCoor;
void main()
{
 gl_Position=mvpMatrix *  vec4(vPosition,1);
 vTextCoor=aTextCoordiate;
}