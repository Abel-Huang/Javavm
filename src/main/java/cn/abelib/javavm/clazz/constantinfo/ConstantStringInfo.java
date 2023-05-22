package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:23
 * ConstantStringInfo常量表示java.lang.String字面量
 */
public class ConstantStringInfo implements ConstantInfo {
    private ConstantPool constantPool;
    private int stringIndex;

    public ConstantStringInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.stringIndex = reader.readUInt16();
    }

    @Override
    public String toString() {
        return this.constantPool.getUtf8(stringIndex);
    }
}
