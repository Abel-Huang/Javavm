package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 17:46
 */
public class AttributeInfos {
    /**
     * @param reader
     * @param constantPool
     * @return
     */
    public static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        int attributesCount = reader.readUInt16();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i ++)  {
            attributes[i] = readAttribute(reader, constantPool);
        }
        return attributes;
    }

    private static AttributeInfo readAttribute(ClassReader reader, ConstantPool cp) {
        int attrNameIndex = reader.readUInt16();
        String attrName = cp.getUtf8(attrNameIndex);
        long attrLen = reader.readUInt32();
        AttributeInfo attrInfo = newAttributeInfo(attrName, attrLen, cp);
        attrInfo.readInfo(reader);
        return attrInfo;
    }

    private static AttributeInfo newAttributeInfo(String attrName, long attrLen, ConstantPool cp) {
        switch (attrName) {
            case "Code":
                return new CodeAttribute(cp);
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "Exceptions":
                return new ExceptionsAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "SourceFile":
                return new SourceFileAttribute(cp);
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                return new UnparsedAttribute(attrName, attrLen, null);
        }
    }
}
