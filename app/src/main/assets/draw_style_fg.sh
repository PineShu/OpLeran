#version 300 es
precision  mediump  float;
in vec4 fColor;
out vec4 toFcolor;
in vec3 vPosition;
void main()
{
  toFcolor=fColor;
}