package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:22
 */
public class ConstantIntegerInfo implements ConstantInfo {
    private int value;

    /**
     * 先读取u32位，然后转换为int32
     * @param reader
     */
    @Override
    public void readInfo(ClassReader reader) {
        long val = reader.readUInt32();
        this.value = Integer.parseUnsignedInt(Long.toUnsignedString(val));
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public int getValue() {
        return value;
    }
}
