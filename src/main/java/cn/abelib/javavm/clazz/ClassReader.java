package cn.abelib.javavm.clazz;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 18:57
 */
public class ClassReader {
    private ByteBuffer byteBuffer;

    public ClassReader(byte[] data) {
        byteBuffer = ByteBuffer.wrap(data);
        // Java 字节码默认是大端
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
    }

    /**
     * u1
     * uint8
     * @return
     */
    public int readUInt8() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        byte b = byteBuffer.get();
        return Byte.toUnsignedInt(b);
    }

    /**
     * u2
     * uint16 -> short
     * 对应 Java int
     * @return
     */
     public int readUInt16() {
         if (!byteBuffer.hasRemaining()) {
             return -1;
         }
         short val = byteBuffer.getShort();
         return Short.toUnsignedInt(val);
     }

    /**
     * 其中第一个uint16表示数组长度
     * uint16数组
     * @return
     */
    public int[] readUInt16s() {
        int len = readUInt16();
        if (len < 1) {
            return new int[0];
        }
        int[] data = new int[len];
        for (int i = 0; i< len; i ++) {
            data[i] = readUInt16();
        }
        return data;
    }

    /**
     * u4
     * uint32 -> int
     * @return
     */
    public long readUInt32() {
        if (!byteBuffer.hasRemaining()) {
            return -1L;
        }
        int val = byteBuffer.getInt();
        return Integer.toUnsignedLong(val);
    }

    /**
     * u4
     * uint32 -> int
     * @return
     */
    public int readInt32() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        return byteBuffer.getInt();
    }

    /**
     * @return
     */
    public long readLong() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        return byteBuffer.getLong();
    }

    public double readDouble() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        return byteBuffer.getDouble();
    }

    /**
     * @return
     */
    public float readFloat() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        return byteBuffer.getFloat();
    }

    public byte[] readBytes(int len) {
        if (!byteBuffer.hasRemaining()) {
          return new byte[0];
        }
        byte[] bytes = new byte[len];
        byteBuffer.get(bytes);
        return bytes;
    }
}
