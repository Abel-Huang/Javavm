package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.*;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/20 23:51
 */
public class RuntimeConstantPool {
    private Clazz clazz;
    private RuntimeConstantPoolInfo[] consts;

    /**
     * ConstantPool -> RuntimeConstantPool
     * @param clazz
     * @param constantPool
     */
    public RuntimeConstantPool(Clazz clazz, ConstantPool constantPool) {
        this.clazz = clazz;
        int cpCount = constantPool.length();
        consts = new RuntimeConstantPoolInfo[cpCount];
        ConstantInfo[] constantInfos = constantPool.getConstantInfos();
        for (int i = 1; i < cpCount; i++) {
            ConstantInfo cpInfo = constantInfos[i];
           if (cpInfo instanceof ConstantIntegerInfo) {
               ConstantIntegerInfo integerInfo = (ConstantIntegerInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setIntValue(integerInfo.getValue());
               continue;
           } else if (cpInfo instanceof ConstantFloatInfo) {
               ConstantFloatInfo floatInfo = (ConstantFloatInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setFloatValue(floatInfo.getValue());
               continue;
           } else if (cpInfo instanceof ConstantLongInfo) {
               ConstantLongInfo longInfo = (ConstantLongInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setLongValue(longInfo.getValue());
               i++;
               continue;
           } else if (cpInfo instanceof ConstantDoubleInfo) {
               ConstantDoubleInfo doubleInfo = (ConstantDoubleInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setDoubleValue(doubleInfo.getValue());
               i++;
               continue;
           } else if (cpInfo instanceof ConstantUtf8Info) {
               ConstantUtf8Info utf8Info = (ConstantUtf8Info)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setUtf8Value(utf8Info.getValue());
               continue;
           } else if (cpInfo instanceof ConstantStringInfo) {
               ConstantStringInfo stringInfo = (ConstantStringInfo)cpInfo;
               // string
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setStringValue(stringInfo.toString());
               continue;
           } else if (cpInfo instanceof ConstantClassInfo) {
               ConstantClassInfo classInfo = (ConstantClassInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setClassRef(new ClassRef(this, classInfo));
               continue;
           } else if (cpInfo instanceof ConstantFieldRefInfo) {
               ConstantFieldRefInfo fieldInfo = (ConstantFieldRefInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setFieldRef(new FieldRef(this, fieldInfo));
               continue;
           } else if (cpInfo instanceof ConstantMethodRefInfo) {
               ConstantMethodRefInfo methodrefInfo = (ConstantMethodRefInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setMethodRef(new MethodRef(this, methodrefInfo));
               continue;
           } else if (cpInfo instanceof ConstantInterfaceMethodRefInfo) {
               ConstantInterfaceMethodRefInfo methodrefInfo = (ConstantInterfaceMethodRefInfo)cpInfo;
               consts[i] = new RuntimeConstantPoolInfo();
               consts[i].setInterfaceMethodRef(new InterfaceMethodRef(this, methodrefInfo));
               continue;
           } else if (cpInfo instanceof ConstantNameAndTypeInfo) {
               continue;
           } else if (cpInfo instanceof ConstantMethodTypeInfo) {
               continue;
           } else if (cpInfo instanceof ConstantMethodHandleInfo) {
               continue;
           } else if (cpInfo instanceof ConstantInvokeDynamicInfo) {
               continue;
           }
           throw new ClassFormatException("java.lang.ClassFormatError:constantpooltag!");
        }
    }

    public RuntimeConstantPoolInfo getConstant(int index) {
        RuntimeConstantPoolInfo constantPoolInfo = this.consts[index];
        if (constantPoolInfo != null) {
            return constantPoolInfo;
        }
       throw new RuntimeException("No constants at index" + index);
    }

    public Clazz getClazz() {
        return clazz;
    }
}
