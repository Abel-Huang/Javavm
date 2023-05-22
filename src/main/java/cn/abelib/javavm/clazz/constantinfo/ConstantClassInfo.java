package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 0:12
 * 表示类或者接口的符号引用
 * 类和超类索引，以及接口表中的接口索引
 */
public class ConstantClassInfo implements ConstantInfo{
    private ConstantPool constantPool;
    private int nameIndex;

    public ConstantClassInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.nameIndex = reader.readUInt16();
    }

    public String name() {
        return this.constantPool.getUtf8(this.nameIndex);
    }

    public int getNameIndex() {
        return nameIndex;
    }

    @Override
    public String toString() {
        return name();
    }
}
