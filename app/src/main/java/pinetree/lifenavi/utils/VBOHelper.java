package pinetree.lifenavi.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class VBOHelper {
    /**
     * 根据传入的数据生成相对应的和系统 相匹配的数据
     *
     * @param data
     * @return
     */
    public static FloatBuffer getFloagBufferData(float[] data) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(data.length * 4);
        vbb.order(ByteOrder.nativeOrder());    //设置字节顺序为本地操作系统顺序
        FloatBuffer mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(data);//将顶点坐标数据放进缓冲
        mVertexBuffer.position(0);
        return mVertexBuffer;
    }
}
