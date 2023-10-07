package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:34
 */
public class ConstantInvokeDynamicInfo implements ConstantInfo {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.bootstrapMethodAttrIndex = reader.readUInt16();
        this.nameAndTypeIndex = reader.readUInt16();
    }
}
