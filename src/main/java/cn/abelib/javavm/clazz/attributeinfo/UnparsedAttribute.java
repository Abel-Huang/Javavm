package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:26
 */
public class UnparsedAttribute implements AttributeInfo {
    private String name;
    private long length;
    private byte[] info;

    public UnparsedAttribute(String attrName, long attrLen, Object o) {
        this.name = attrName;
        this.length = attrLen;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.info = reader.readBytes((int) length);
    }
}
