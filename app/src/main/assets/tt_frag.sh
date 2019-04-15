#version 300 es
precision mediump  float;
uniform  sampler2D sTexture;
in vec2 aTextCoordiate;
out vec4 fragColor;
void main()
{
fragColor=texture(sTexture,aTextCoordiate);
}
