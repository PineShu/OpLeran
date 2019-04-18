#version 300 es
uniform mat4 mvpMatrix;
in vec3 aPosition;
in vec2 texureCoord;
out vec2 vTexture;
void  main()
{
  gl_Position=mvpMatrix * (aPosition,1);
  vTexture=texureCoord;
}