package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 0:09
 * 字符CONSTANTClassinfo和CONSTANT
 */
public class ConstantNameAndTypeInfo implements ConstantInfo {
    private ConstantPool constantPool;
    private int nameIndex;
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.nameIndex = reader.readUInt16();
        this.descriptorIndex = reader.readUInt16();
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    @Override
    public String toString() {
        String name = this.constantPool.getUtf8(this.getNameIndex());
        String type = this.constantPool.getUtf8(this.getDescriptorIndex());

        return name + ":" + type;
    }
}
