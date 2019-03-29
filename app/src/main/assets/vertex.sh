#!/usr/bin/env bash
#version 300 es
uniform mat4 uMVPMatrix;//总变换矩阵
in vec3 aPosition; //顶点位置
in vec4 aColor;  //顶点颜色
out vec4 toColor; //用于传递给片元这色器变量
void main(){
gl_Position=uMVPMatrix * vec4(aPosition,1);
toColor=aColor;
         }