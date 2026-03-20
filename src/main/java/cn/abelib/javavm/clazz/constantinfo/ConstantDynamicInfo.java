package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * CONSTANT_Dynamic_info structure (tag=17)
 * Used for constant dynamic in Java 11+
 */
public class ConstantDynamicInfo implements ConstantInfo {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.bootstrapMethodAttrIndex = reader.readUInt16();
        this.nameAndTypeIndex = reader.readUInt16();
    }

    public int getBootstrapMethodAttrIndex() {
        return bootstrapMethodAttrIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }
}
