package cn.abelib.javavm.util;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 18:23
 *
 * unsigned 注释：java 中没有 unsigned，所以为了实现 unsigned，
 * 需要使用比原本类型更大的类型，通过位运算获取其 unsigned 的值\
 * unsigned byte & short -> int，unsigned int -> long
 */
public class NumberUtils {

    public static int getUnsignedByte(byte b) {
        return b & 0x0FF;
    }

    public static int getUnsignedShort(short data) {
        return data & 0x0FFFF;
    }

    /**
     * data & 0xFFFFFFFF 和 data & 0xFFFFFFFFL 结果是不同的，
     * 需要注意，有可能与 JDK 版本有关
     * @param data
     * @return
     */
    public static long getUnsignedInt(int data) {
        return data & 0xFFFFFFFFL;
    }
}
