package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 0:04
 */
public class ConstantLongInfo implements ConstantInfo {
    private long value;
    @Override
    public void readInfo(ClassReader reader) {
        this.value = reader.readLong();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public long getValue() {
        return value;
    }
}
