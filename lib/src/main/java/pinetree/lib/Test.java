package pinetree.lib;

import java.util.Stack;

public class Test {
    private static float[] f=new float[2];
    private static Stack<float[]>  stack=new Stack<>();
    public static void main(String args[]){
        stack.add(f);
        f[1]=12;
        stack.add(f);
        for (float[] f:stack){
            for (float ff:f){
                System.out.println(ff);
            }
        }
    }
}
