package cn.abelib.javavm.instructions.base;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:30
 */
public class BytecodeReader {
    private byte[] code;
    private int pc;

    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }

    public int readUInt8() {
        byte i = this.code[this.pc];
        this.pc++;
        return Byte.toUnsignedInt(i);
    }

    public int readInt8() {
        byte i = this.code[this.pc];
        this.pc++;
        return (int)i;
    }

    public int readInt16() {
        return readUInt16();
    }

    public int readUInt16() {
        int v1 = this.readUInt8();
        int v2 = this.readUInt8();
        return (v1 << 8) | v2;
    }

    public int readInt32() {
        int v1 = this.readUInt8();
        int v2 = this.readUInt8();
        int v3 = this.readUInt8();
        int v4 = this.readUInt8();
        return (v1 << 24) | (v2 << 16) | (v3 << 8) | v4;
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
