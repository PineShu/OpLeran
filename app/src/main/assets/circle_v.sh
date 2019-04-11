# version  300 es
uniform mat4 mMVPMatrix;
in vec3 vPosition;
in vec4 vColor;
out vec4 vvColor;
void main()
{
  gl_Position=mMVPMatrix * vec4(vPosition,1);
  vvColor=vColor;
}