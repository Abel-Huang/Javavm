package cn.abelib.javavm.instructions.base;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:30
 */
public class BytecodeReader {
    private byte[] code;
    private ByteBuffer byteBuffer;
    private int pc;

    public void reset(byte[] code, int pc) {
        byteBuffer = ByteBuffer.wrap(code);
        // Java 字节码默认是大端
        byteBuffer.order(ByteOrder.BIG_ENDIAN);
        this.pc = pc;
    }

    public int readUInt8() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        byte b = byteBuffer.get(this.pc);
        this.pc ++;
        return Byte.toUnsignedInt(b);
    }

    public int readInt8() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        int b = byteBuffer.get(this.pc);
        this.pc ++;
        return b;
    }

    public int readInt16() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        short val = byteBuffer.getShort(this.pc);
        this.pc += 2;
        return val;
    }

    public int readUInt16() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        short val = byteBuffer.getShort(this.pc);
        this.pc += 2;
        return Short.toUnsignedInt(val);
    }

    public int readInt32() {
        if (!byteBuffer.hasRemaining()) {
            return -1;
        }
        int i = byteBuffer.getInt(this.pc);
        this.pc += 4;
        return i;
    }

    public void skipPadding() {
        while (this.pc % 4 != 0){
            this.readInt8();
        }
    }

    public int[] readInt32s(int jumpOffsetsCount) {
        int[] ints = new int[jumpOffsetsCount];
        for ( int i = 0; i < jumpOffsetsCount; i ++) {
            ints[i] = this.readInt32();
        }
        return ints;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }
}
