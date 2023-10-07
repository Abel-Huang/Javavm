package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:34
 */
public class ConstantMethodTypeInfo implements ConstantInfo {
    private int descriptorIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.descriptorIndex = reader.readUInt16();
    }
}
