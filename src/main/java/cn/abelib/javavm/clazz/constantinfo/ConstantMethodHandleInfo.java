package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 23:34
 */
public class ConstantMethodHandleInfo implements ConstantInfo {
    private int referenceKind;
    private int referenceIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.referenceKind = reader.readUInt8();
        this.referenceIndex = reader.readUInt16();
    }
}