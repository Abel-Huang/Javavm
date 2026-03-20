package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * Java 9+ 新增：表示包的符号引用
 */
public class ConstantPackageInfo implements ConstantInfo {
    private ConstantPool constantPool;
    private int nameIndex;

    public ConstantPackageInfo(ConstantPool constantPool) {
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
