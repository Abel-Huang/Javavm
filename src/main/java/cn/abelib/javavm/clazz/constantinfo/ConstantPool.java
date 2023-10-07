package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;
import org.apache.commons.lang3.ArrayUtils;

import static cn.abelib.javavm.clazz.Constant.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 20:08
 */
public class ConstantPool {
    private ConstantInfo[] constantInfos;

    /**
     * 第一，表头给出的常量池大小比实际大1。假设表头给出的值是n，常量池的实际大小是n–1
     * 第二，有效的常量池索引是1~n–1。0是无效索引，表示不指向任何常量
     * 第三，CONSTANT_Long_info和CONSTANT_Double_info各占两个位置
     * @param reader
     */
    public ConstantPool(ClassReader reader) {
        int cpCount = reader.readUInt16();
        constantInfos = new ConstantInfo[cpCount];
        // 注意索引从1开始
        for(int i = 1; i < cpCount; i++) {
            constantInfos[i] = readConstantInfo(reader);
            if (constantInfos[i] instanceof ConstantLongInfo ||
                    constantInfos[i] instanceof ConstantDoubleInfo) {
                // 占两个位置
                i++;
            }
        }
    }

    public ConstantInfo newConstantInfo(int tag, ConstantPool cp) {
        switch (tag) {
            case CONSTANT_Integer:
                return new ConstantIntegerInfo();
            case CONSTANT_Float:
                return new ConstantFloatInfo();
            case CONSTANT_Long:
                return new ConstantLongInfo();
            case CONSTANT_Double:
                return new ConstantDoubleInfo();
            case CONSTANT_Utf8:
                return new ConstantUtf8Info();
            case CONSTANT_String:
                return new ConstantStringInfo(cp);
            case CONSTANT_Class:
                return new ConstantClassInfo(cp);
            case CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(cp);
            case CONSTANT_Methodref:
                return new ConstantMethodRefInfo(cp);
            case CONSTANT_InterfaceMethodref:
                return new ConstantInterfaceMethodRefInfo(cp);
            case CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(cp);
            case CONSTANT_MethodType:
                return new ConstantMethodTypeInfo();
            case CONSTANT_MethodHandle:
                return new ConstantMethodHandleInfo();
            case CONSTANT_InvokeDynamic:
                return new ConstantInvokeDynamicInfo();
            default:
                throw new RuntimeException("java.lang.ClassFormatError:constantpooltag!");
        }
    }

    private ConstantInfo readConstantInfo(ClassReader reader) {
        int tag = reader.readUInt8();
        ConstantInfo constantInfo = newConstantInfo(tag, this);
        constantInfo.readInfo(reader);
        return constantInfo;
    }

    /**
     * 按索引查找常量
     * @param index
     * @return
     */
    public ConstantInfo getConstantInfo(int index) {
        ConstantInfo cpInfo = this.constantInfos[index];
        if (cpInfo != null) {
            return cpInfo;
        }
        throw new RuntimeException("Invalid constant pool index!");
    }

    public NameAndType getNameAndType(int index) {
        ConstantNameAndTypeInfo ntInfo = (ConstantNameAndTypeInfo)this.getConstantInfo(index);
        String name = this.getUtf8(ntInfo.getNameIndex());
        String type = this.getUtf8(ntInfo.getDescriptorIndex());
        return new NameAndType(name, type);
    }

    public String getClassName(int index) {
        ConstantClassInfo classInfo = (ConstantClassInfo) this.getConstantInfo(index);
        return this.getUtf8(classInfo.getNameIndex());
    }

    public String getUtf8(int index) {
        ConstantUtf8Info utf8Info = (ConstantUtf8Info)this.getConstantInfo(index);
        return utf8Info.getValue();
    }

    public ConstantInfo[] getConstantInfos() {
        return constantInfos;
    }

    public int length() {
        if (ArrayUtils.isEmpty(constantInfos)) {
            return 0;
        }
        return ArrayUtils.getLength(constantInfos);
    }
}
