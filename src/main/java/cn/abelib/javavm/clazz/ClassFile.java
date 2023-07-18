package cn.abelib.javavm.clazz;

import cn.abelib.javavm.Constants;
import cn.abelib.javavm.clazz.attributeinfo.AttributeInfo;
import cn.abelib.javavm.clazz.attributeinfo.AttributeInfos;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 19:00
 */
public class ClassFile {
    private long magic;

    private int minorVersion;

    private int majorVersion;

    private ConstantPool constantPool;

    private int accessFlags;

    private int thisClass;

    private int superClass;

    private int[] interfaces;

    private MemberInfo[] fields;

    private MemberInfo[] methods;

    private AttributeInfo[] attributes;

    public ClassFile() {}
    
    public void parse(byte[] data) {
        ClassReader cr = new ClassReader(data);
        this.read(cr);
    }
    
    public void read(ClassReader reader) {
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantPool = readConstantPool(reader);
        this.accessFlags = reader.readUInt16();
        this.thisClass = reader.readUInt16();
        this.superClass = reader.readUInt16();
        this.interfaces = reader.readUInt16s();
        this.fields = readMembers(reader, this.constantPool);
        this.methods = readMembers(reader, this.constantPool);
        this.attributes = readAttributes(reader, this.constantPool);
    }

    private AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantPool) {
        return AttributeInfos.readAttributes(reader, constantPool);
    }

    private MemberInfo[] readMembers(ClassReader reader, ConstantPool constantPool) {
        int memberCount = reader.readUInt16();
        // 循环读取
        MemberInfo[] members = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i ++) {
            members[i] = new MemberInfo(reader, constantPool);
        }
        return members;
    }

    private ConstantPool readConstantPool(ClassReader reader) {
        return new ConstantPool(reader);
    }

    public void readAndCheckMagic(ClassReader reader) {
        this.magic = reader.readUInt32();
        if (this.magic != Constants.MAGIC_NUMBER) {
            throw new RuntimeException("java.lang.ClassFormatError: magic!");
        }
    }
    
    public void readAndCheckVersion(ClassReader reader) {
        this.minorVersion = reader.readUInt16();
        this.majorVersion = reader.readUInt16();
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (this.minorVersion == 0) {
                    return;
                }
        }
        throw new RuntimeException("java.lang.UnsupportedClassVersionError!");
    }
    
    public int getMinorVersion() {
        return this.minorVersion;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public ConstantPool getConstantPool() {
        return this.constantPool;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public MemberInfo[] getFields() {
        return this.fields;
    }

    public MemberInfo[] getMethods() {
        return this.methods;
    }
    
    public String getClassName() {
        return this.constantPool.getClassName(this.thisClass);
    }
    
    public String getSuperClassName() {
        if (this.superClass > 0) {
            return this.constantPool.getClassName(this.superClass);
        }
        // 只有java.lang.Object没有超类
        return "";
    }

    public String[] getInterfaceNames() {
        String[] interfaceNames =  new String[this.interfaces.length];
        for(int i = 0; i < this.interfaces.length; i ++) {
            interfaceNames[i] = this.constantPool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }
}
