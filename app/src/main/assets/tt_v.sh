#version 300 es
uniform  mat4  mvpMatrix;
in vec3 vPosition
in vec2 aTextCoordiate;
void main()
{
 gl_Position=mvpMatrix *  vec4(vPosition,1);
}