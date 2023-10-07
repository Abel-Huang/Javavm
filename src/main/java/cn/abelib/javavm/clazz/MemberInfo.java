package cn.abelib.javavm.clazz;

import cn.abelib.javavm.clazz.attributeinfo.AttributeInfo;
import cn.abelib.javavm.clazz.attributeinfo.AttributeInfos;
import cn.abelib.javavm.clazz.attributeinfo.CodeAttribute;
import cn.abelib.javavm.clazz.attributeinfo.ConstantValueAttribute;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 22:40
 */
public class MemberInfo {
    private ConstantPool constantPool;

    private int accessFlags;

    private int nameIndex;

    private int descriptorIndex;

    private AttributeInfo[] attributes;

    /**
     * member name
     */
    private String name;
    /**
     * member descriptor
     */
    private String descriptor;

    public MemberInfo(ClassReader reader, ConstantPool constantPool) {
        this.constantPool = constantPool;
        this.accessFlags = reader.readUInt16();
        this.nameIndex = reader.readUInt16();
        if (this.nameIndex > 0) {
            this.name = this.constantPool.getUtf8(this.nameIndex);
        }
        this.descriptorIndex = reader.readUInt16();
        if (this.descriptorIndex > 0) {
            this.descriptor = this.constantPool.getUtf8(this.descriptorIndex);
        }
        this.attributes = AttributeInfos.readAttributes(reader, constantPool);
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public AttributeInfo[] getAttributes() {
        return attributes;
    }

    public String getName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    /**
     * get CodeAttribute
     * @return
     */
    public CodeAttribute getCodeAttribute() {
        for(AttributeInfo attrInfo : this.attributes) {
            if (attrInfo instanceof CodeAttribute) {
                return (CodeAttribute) attrInfo;
            }
        }
        return null;
    }

    public ConstantValueAttribute getConstantValueAttribute() {
        for(AttributeInfo attrInfo : this.attributes) {
            if (attrInfo instanceof ConstantValueAttribute) {
                return (ConstantValueAttribute) attrInfo;
            }
        }
        return null;
    }
}
