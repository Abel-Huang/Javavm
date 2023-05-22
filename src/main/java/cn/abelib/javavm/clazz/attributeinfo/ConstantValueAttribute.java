package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:25
 */
public class ConstantValueAttribute implements AttributeInfo {
    private int constantValueIndex;

    @Override
    public void readInfo(ClassReader reader) {
        this.constantValueIndex = reader.readUInt16();
    }

    public int getConstantValueIndex() {
        return this.constantValueIndex;
    }
}
