package pinetree.lifenavi.utils;

import android.content.res.Resources;
import android.opengl.GLES30;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import pinetree.lifenavi.log.Log;

public class ShaderUtils {

    /**
     * 加载shader
     */
    public static int loadShader(int shaderType, String source) {
        //创建shader  就是创建要给绘制的图形
        int shader = GLES30.glCreateShader(shaderType);
        if (shader != 0) {
            //加载源码
            GLES30.glShaderSource(shader, source);
            //编译shader
            GLES30.glCompileShader(shader);
            int[] complier = new int[1];
            //获取编译结果
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, complier, 0);
            if (complier[1] == 0) {
                //编译失败
                String log = GLES30.glGetShaderInfoLog(shader);
                Log.e("LoadShader", log);
                GLES30.glDeleteShader(shader);
            }

        }
        return shader;
    }


    public static int createProgram(String vertexSource, String fragemntSource) {
        int verterShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragemntSource);
        //如果有一个加载失败就不能继续了
        if (verterShader == 0 || fragmentShader == 0) {
            return -1;
        }
        int program = GLES30.glCreateProgram();
        if (program != 0) {//创建成功
            //程序中添加 shader
            GLES30.glAttachShader(program, verterShader);
            GLES30.glAttachShader(program, fragmentShader);
            checkGlError("GL_AtttachShader");
            //连接程序到GL管线
            GLES30.glLinkProgram(program);
            int[]  links=new int[1];
            //检查连接状态
            GLES30.glGetProgramiv(program,GLES30.GL_LINK_STATUS,links,0);
            if (links[0]==0){
                String log=GLES30.glGetProgramInfoLog(program);
                Log.e("Create_Program",log);
                GLES30.glDeleteProgram(program);
            }
        }
        return program;
    }


    //检查每一步操作是否有错误的方法
    public static void checkGlError(String op)
    {
        int error;
        while ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR)
        {
            Log.e("ES30_ERROR", op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }


    //从sh脚本中加载shader内容的方法
    public static String loadFromAssetsFile(String fname,Resources r)
    {
        String result=null;
        try
        {
            InputStream in=r.getAssets().open(fname);
            int ch=0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while((ch=in.read())!=-1)
            {
                baos.write(ch);
            }
            byte[] buff=baos.toByteArray();
            baos.close();
            in.close();
            result=new String(buff,"UTF-8");
            result=result.replaceAll("\\r\\n","\n");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
