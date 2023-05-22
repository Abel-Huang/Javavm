package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;
import cn.abelib.javavm.clazz.LocalVariableTableEntry;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:25
 * 属性表中存放方法的局部变量信息
 */
public class LocalVariableTableAttribute implements AttributeInfo {
    private LocalVariableTableEntry[] localVariableTable;

    @Override
    public void readInfo(ClassReader reader) {
        int localVariableLength = reader.readUInt16();
        this.localVariableTable = new LocalVariableTableEntry[localVariableLength];
        for (int i = 0; i < localVariableLength; i ++){
            localVariableTable[i] = new LocalVariableTableEntry(reader.readUInt16(),
                    reader.readUInt16(),
                    reader.readUInt16(),
                    reader.readUInt16(),
                    reader.readUInt16());
        }
    }
}
