package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:23
 */
public class ConstantFloatInfo implements ConstantInfo {
    private float value;

    @Override
    public void readInfo(ClassReader reader) {
        this.value = reader.readFloat();
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public float getValue() {
        return value;
    }
}
