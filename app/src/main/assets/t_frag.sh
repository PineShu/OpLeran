#version  300 es
precision  mediump float;
in vec2 vTextCoor;
uniform  sampler2D  sTexture;
out vec4 fragColor;
void main()
{
  fragColor=texture(sTexture,vTextCoor);
}
