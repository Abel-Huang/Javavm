package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:36
 */
public class ConstantMemberRefInfo implements ConstantInfo {
    private ConstantPool constantPool;
    private int classIndex;
    private int nameAndTypeIndex;

    public ConstantMemberRefInfo(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.classIndex = reader.readUInt16();
        this.nameAndTypeIndex = reader.readUInt16();
    }

    public String getClassName() {
        return this.constantPool.getClassName(this.classIndex);
    }

    public NameAndType getNameAndDescriptor() {
        return this.constantPool.getNameAndType(this.nameAndTypeIndex);
    }

    @Override
    public String toString() {
        NameAndType nameAndType = getNameAndDescriptor();
        return getClassName() + ":" + nameAndType.getName() + ":" + nameAndType.getType();
    }
}
