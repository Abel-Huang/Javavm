package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 0:05
 */
public class ConstantDoubleInfo implements ConstantInfo {
    private double value;

    /**
     * @param reader
     */
    @Override
    public void readInfo(ClassReader reader) {
        this.value = reader.readDouble();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public double getValue() {
        return value;
    }
}
