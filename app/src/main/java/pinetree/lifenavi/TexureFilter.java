package pinetree.lifenavi;

/**
 * 纹理采样：片元的纹理坐标与纹理图并不一一对应，较小的纹理图映射到大的图元，反之亦然，都不能保证
 * 这时候就需要一定的策越是的纹理采样可以顺利进行下去。
 * 策略有两种：
 *  1.近点采样
 *  2.线性采样
 *
 *  近点采样：纹理图是按照每个像素点组成的，片元对应的纹理坐标进行取点采样。
 *           缺点：会产生锯齿，在较小的纹理图映射到较大的图元比较明显，反之不容易察觉。
 *  线性采样：结果颜色并不来自纹理图中的一个元素，在采样时会考虑到片元对应的纹理坐标附近的个元素进行平滑。
 *            缺点：较小的纹理图映射到较大的图元不再会有锯齿的现象，是平滑过渡的，平滑过渡解决了锯齿问题，但同时线条 边缘会模糊。
 *
 * MIN与MAG采样方式 当纹理图中的一个像素对应到待映射图元上的多个片元时，采用MAG采样，反之采用MIN采样。
 *   MIN:当一副较大的纹理图映射到一个 较小的图元采用的方式
 *   MAG:当一个较小的纹理图映射到一个 较大的图元采用的方式
 *  提示：
 *        由于最近点采样计算速度快，在MIN的情况下一般锯齿不明显综合收益高。
 *        MAG方式采用近点锯齿严重。
 *        在实际开发中一般 将MIN设置为近点采样，将MAG设置为线性采样的组合。
 */
public class TexureFilter {

}