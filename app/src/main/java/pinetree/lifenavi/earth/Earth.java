package pinetree.lifenavi.earth;

import android.opengl.GLES30;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import pinetree.lifenavi.utils.MatrixHelper;
import pinetree.lifenavi.utils.ShaderUtil;
import pinetree.lifenavi.utils.VBOHelper;

public class Earth {

    private String vertex = "#version 300 es\n" +
            "uniform mat4 mvpMatrix;\n" +
            "uniform mat4 mMatrix;\n" +
            "uniform vec3 cameraLoc;\n" +
            "uniform vec3 lightLoc;\n" +
            "in vec3 aPosition;\n" +
            "in vec2 aTexture;//纹理坐标\n" +
            "in vec3 vNormal;// 法向量\n" +
            "out vec2 textureCoord;\n" +
            "out vec4 vAbminet;//环境光\n" +
            "out vec4 vDiffuse;//散射光\n" +
            "out vec4 vSpecular;//镜面光\n" +
            "void lightCaculate(in vec3 normal,inout vec4 ambient,inout vec4 diffuse,inout vec4 specular,in vec3 lightLocation,in vec4 lAmbinet,in vec4 lDiffuse,in vec4 lSpecular)\n" +
            "{" +
            "  ambient=lAmbinet;//环境光是360度均匀的 所以直接给出环境光\n" +
            "  //散射光  散射光：散射光系数*散射光强度*max（0.0，cos(法向量，光照位置)）// cos -1---->1 的取值范围   cos0=1  cos90=0  cos180=-1\n" +
            "  vec3 normalTarget=aPosition+normal;//变换后的法向量，法向量与顶点位置相同 所以向量没有出物体，相加让向量出物体\n" +
            "  vec3 newNormal=(mMatrix*vec4(normalTarget,1)-(mMatrix*vec4(aPosition,1))).xyz;//得到从物体表面顶点到 物体外的向量\n" +
            "  newNormal=normalize(newNormal);//规格化法向量\n" +
            "  //计算从表面点到相机的向量\n" +
            "  vec3 eye=normalize(cameraLoc-(mMatrix*vec4(aPosition,1)).xyz);\n" +
            "  //计算从表面点到光源位置的向量\n" +
            "  vec3 vp=normalize(lightLocation-(mMatrix*vec4(aPosition,1)).xyz);\n" +
            "  // 散射光 的最终强度\n" +
            "  // 法向量与光照的 cos值\n" +
            "  float dotSize=max(0.0,dot(newNormal,vp));\n" +
            "  diffuse=lDiffuse*dotSize;\n" +
            "  //镜面光  镜面光的系数*镜面光强度*max(0.0,cos(半向量，法向量)(粗糙度))\n" +
            "  //求半向量\n" +
            "  vec3 halfVec=normalize(vp+eye);\n" +
            "  float shines=50.0;\n" +
            "  float sDotSize=dot(newNormal,halfVec);//法向量与半向量的点积\n" +
            "  float specularFactor=max(0.0,pow(sDotSize,shines)); //镜面光强度因子\n" +
            "  specular=lSpecular*specularFactor;//镜面光的最终强度\n" +
            "}\n" +
            "\n" +
            "void main()\n" +
            "{" +
            "   gl_Position=mvpMatrix*vec4(aPosition,1);\n" +
            "   vec4 ambientTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "   vec4 diffuseTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "   vec4 specularTemp=vec4(0.0,0.0,0.0,0.0);\n" +
            "   lightCaculate(normalize(vNormal),ambientTemp,diffuseTemp,specularTemp,lightLoc,vec4(0.05,0.05,0.025,1.0),vec4(1.0,1.0,0.5,1.0),vec4(0.3,0.3,0.15,1.0));\n" +
            "   vAbminet=ambientTemp;\n" +
            "   vDiffuse=diffuseTemp;\n" +
            "   vSpecular=specularTemp;\n" +
            "" +
            "   textureCoord=aTexture;\n" +
            "}\n";
    private String frag = "#version 300 es\n" +
            "precision  mediump  float;\n" +
            "in vec2 textureCoord;\n" +
            "in vec4 vAbminet;\n" +
            "in vec4 vDiffuse;\n" +
            "in vec4 vSpecular;\n" +
            "uniform  sampler2D sTextureDay;//纹理数据内容\n" +
            "uniform  sampler2D sTextureNight;//纹理数据内容\n" +
            "out vec4 fragColor;\n" +
            "void main()\n" +
            "{\n" +
            " //地球着色器的main方法\n" +
            "  vec4 finalColorDay;  //从白天纹理中采样出颜色值\n" +
            "  vec4 finalColorNight;   //从夜晚纹理中采样出颜色值\n" +
            "\n" +
            "  finalColorDay= texture(sTextureDay, textureCoord);//采样出白天纹理的颜色值\n" +
            "  finalColorDay = finalColorDay*vAbminet+finalColorDay*vDiffuse+finalColorDay*vDiffuse;\n" +
            "  finalColorNight = texture(sTextureNight, textureCoord);  //采样出夜晚纹理的颜色值\n" +
            "  finalColorNight = finalColorNight*vec4(0.5,0.5,0.5,1.0);//计算出的该片元夜晚颜色值\n" +
            "  \n" +
            "  if(vDiffuse.x>0.21)\n" +
            "  {//当散射光分量大于0.21时\n" +
            "    fragColor=finalColorDay;   //采用白天纹理 \n" +
            "  } \n" +
            "  else if(vDiffuse.x<0.05)\n" +
            "  {     //当散射光分量小于0.05时\n" +
            "     fragColor=finalColorNight;//采用夜间纹理\n" +
            "  }\n" +
            "  else\n" +
            "  {\t//当环境光分量大于0.05小于0.21时，为白天夜间纹理的过渡阶段\n" +
            "     float t=(vDiffuse.x-0.05)/0.16;//计算白天纹理应占纹理过渡阶段的百分比\n" +
            "     fragColor=t*finalColorDay+(1.0-t)*finalColorNight;//计算白天黑夜过渡阶段的颜色值\n" +
            "  }  \n" +
            "}";

    private int program;
    private int mvpMatrix;
    private int mMatrix;
    private int cameraLoc;
    private int lightLoc;
    private int position;
    private int normal;
    private int textureCoord;
    int uDayTexHandle;//白天纹理属性引用
    int uNightTexHandle;//黑夜纹理属性引用
    private int vCount;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexCoorBuffer;

    public Earth(float r) {
        initShader();
        initVertexData(r);
    }

    private void initShader() {
        program = ShaderUtil.createProgram(vertex, frag, this.getClass().getName());
        mvpMatrix = GLES30.glGetUniformLocation(program, "mvpMatrix");
        mMatrix = GLES30.glGetUniformLocation(program, "mMatrix");
        cameraLoc = GLES30.glGetUniformLocation(program, "cameraLoc");
        lightLoc = GLES30.glGetUniformLocation(program, "lightLoc");
        normal = GLES30.glGetAttribLocation(program, "vNormal");
        textureCoord = GLES30.glGetAttribLocation(program, "aTexture");
        position = GLES30.glGetAttribLocation(program, "aPosition");
        uDayTexHandle=GLES30.glGetUniformLocation(program, "sTextureDay");
        uNightTexHandle=GLES30.glGetUniformLocation(program, "sTextureNight");
    }

    //初始化顶点数据的方法
    public void initVertexData(float r) {
        //顶点坐标数据的初始化================begin============================
        final float UNIT_SIZE = 0.5f;
        ArrayList<Float> alVertix = new ArrayList<Float>();//存放顶点坐标的ArrayList
        final float angleSpan = 10f;//将球进行单位切分的角度
        for (float vAngle = 90; vAngle > -90; vAngle = vAngle - angleSpan)//垂直方向angleSpan度一份
        {
            for (float hAngle = 360; hAngle > 0; hAngle = hAngle - angleSpan)//水平方向angleSpan度一份
            {//纵向横向各到一个角度后计算对应的此点在球面上的坐标
                double xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                float x1 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z1 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y1 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x2 = (float) (xozLength * Math.cos(Math.toRadians(hAngle)));
                float z2 = (float) (xozLength * Math.sin(Math.toRadians(hAngle)));
                float y2 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));

                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle - angleSpan));
                float x3 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z3 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y3 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle - angleSpan)));

                xozLength = r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle));
                float x4 = (float) (xozLength * Math.cos(Math.toRadians(hAngle - angleSpan)));
                float z4 = (float) (xozLength * Math.sin(Math.toRadians(hAngle - angleSpan)));
                float y4 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                //构建第一三角形
                alVertix.add(x1);
                alVertix.add(y1);
                alVertix.add(z1);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                //构建第二三角形
                alVertix.add(x4);
                alVertix.add(y4);
                alVertix.add(z4);
                alVertix.add(x2);
                alVertix.add(y2);
                alVertix.add(z2);
                alVertix.add(x3);
                alVertix.add(y3);
                alVertix.add(z3);
            }
        }
        vCount = alVertix.size() / 3;//顶点的数量为坐标值数量的1/3，因为一个顶点有3个坐标

        //将alVertix中的坐标值转存到一个float数组中
        float vertices[] = new float[vCount * 3];
        for (int i = 0; i < alVertix.size(); i++) {
            vertices[i] = alVertix.get(i);
        }

        mVertexBuffer = VBOHelper.getFloagBufferData(vertices);

        //将alTexCoor中的纹理坐标值转存到一个float数组中
        //获取切分整图的纹理数组
        float[] texCoor = generateTexCoor(
                (int) (360 / angleSpan), //纹理图切分的列数
                (int) (180 / angleSpan)  //纹理图切分的行数
        );
        mTexCoorBuffer = VBOHelper.getFloagBufferData(texCoor);
        //顶点坐标数据的初始化================end============================
    }

    //自动切分纹理产生纹理数组的方法
    private float[] generateTexCoor(int bw, int bh) {
        float[] result = new float[bw * bh * 6 * 2];
        float sizew = 1.0f / bw;//列数
        float sizeh = 1.0f / bh;//行数
        int c = 0;
        for (int i = 0; i < bh; i++) {
            for (int j = 0; j < bw; j++) {
                //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
                float s = j * sizew;
                float t = i * sizeh;

                result[c++] = s;
                result[c++] = t;

                result[c++] = s;
                result[c++] = t + sizeh;

                result[c++] = s + sizew;
                result[c++] = t;

                result[c++] = s + sizew;
                result[c++] = t;

                result[c++] = s;
                result[c++] = t + sizeh;

                result[c++] = s + sizew;
                result[c++] = t + sizeh;
            }
        }
        return result;
    }
    public void drawSelf(int texId,int texIdNight)
    {
        //指定使用某套shader程序
        GLES30.glUseProgram(program);
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(mvpMatrix, 1, false, MatrixHelper.getFinalMatrix(), 0);
        //将位置、旋转变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(mMatrix, 1, false, MatrixHelper.getMMatrix(), 0);
        //将摄像机位置传入渲染管线
        GLES30.glUniform3fv(cameraLoc, 1, MatrixHelper.getCameraLocationBuffer());
        //将光源位置传入渲染管线
        GLES30.glUniform3fv(lightLoc, 1, MatrixHelper.getLightLocation());

        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        position,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //将顶点纹理数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        textureCoord,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2*4,
                        mTexCoorBuffer
                );
        //将顶点法向量数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        normal,
                        4,
                        GLES30.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(position);
        //启用顶点纹理数据数组
        GLES30.glEnableVertexAttribArray(textureCoord);
        //启用顶点法向量数据数组
        GLES30.glEnableVertexAttribArray(normal);
        //绑定纹理
        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);   //白天纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texIdNight);  //黑夜纹理
        GLES30.glUniform1i(uDayTexHandle, 0);//通过引用指定白天纹理
        GLES30.glUniform1i(uNightTexHandle, 1);  //通过引用指定黑夜纹理
        //以三角形方式执行绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

}
