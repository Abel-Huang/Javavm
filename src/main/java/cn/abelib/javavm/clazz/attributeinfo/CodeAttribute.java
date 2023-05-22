package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;
import cn.abelib.javavm.clazz.ExceptionTableEntry;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:25
 */
public class CodeAttribute implements AttributeInfo {
    private ConstantPool cp;
    private int maxStack;
    private int maxLocals;
    private byte[] code;
    private ExceptionTableEntry[] exceptionTable;
    private AttributeInfo[] attributes;

    public CodeAttribute(ConstantPool cp) {
        this.cp = cp;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.maxStack = reader.readUInt16();
        this.maxLocals = reader.readUInt16();
        long codeLength = reader.readUInt32();
        this.code = reader.readBytes((int) codeLength);
        this.exceptionTable = readExceptionTable(reader);
        this.attributes = AttributeInfos.readAttributes(reader, this.cp);
    }

    private ExceptionTableEntry[] readExceptionTable(ClassReader reader) {
        int exceptionTableLength = reader.readUInt16();
        this.exceptionTable = new ExceptionTableEntry[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i ++){
            exceptionTable[i] = new ExceptionTableEntry(reader.readUInt16(),
                    reader.readUInt16(),
                    reader.readUInt16(),
                    reader.readUInt16());
        }
        return exceptionTable;
    }

    public ConstantPool getCp() {
        return cp;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public byte[] getCode() {
        return code;
    }
}
