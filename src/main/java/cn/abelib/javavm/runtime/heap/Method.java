package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.ExceptionHandler;
import cn.abelib.javavm.clazz.ExceptionTable;
import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.attributeinfo.CodeAttribute;
import cn.abelib.javavm.clazz.attributeinfo.LineNumberTableAttribute;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/12 23:54
 */
public class Method extends ClassMember {
    private int maxStack;
    private int maxLocals;
    private byte[] code;
    private int argSlotCount;
    private MethodDescriptor parsedDescriptor;
    private ExceptionTable exceptionTable;
    private LineNumberTableAttribute lineNumberTable;

    public Method(Clazz clazz, MemberInfo memberInfo) {
        this.setClazz(clazz);
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
        this.calcArgSlotCount();

        if (this.isNative()) {
            this.injectCodeAttribute(this.parsedDescriptor.getReturnType());
        }
    }

    /**
     * todo
     *
     * @param returnType
     */
    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4;
        this.maxLocals = this.argSlotCount;
        switch (returnType.charAt(0)) {
            case 'V':
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb1};
            case 'D':
                // dreturn
                this.code = new byte[]{(byte) 0xfe, (byte) 0xaf};
            case 'F':
                // freturn
                this.code = new byte[]{(byte) 0xfe, (byte) 0xae};
            case 'J':
                // lreturn
                this.code = new byte[]{(byte) 0xfe, (byte) 0xad};
            case 'L':
            case '[':
                // areturn
                this.code = new byte[]{(byte) 0xfe, (byte) 0xb0};
            default:
                // ireturn
                this.code = new byte[]{(byte) 0xfe, (byte) 0xac};
        }
    }

    private void calcArgSlotCount() {
        this.parsedDescriptor = new MethodDescriptor() ;
        parsedDescriptor.parseMethodDescriptor(super.descriptor);
        if (CollectionUtils.isEmpty(parsedDescriptor.getParameterTypes())) {
            return;
        }
        for (String paramType : parsedDescriptor.getParameterTypes()) {
            this.argSlotCount++;
            if (paramType.equals(SymbolicReferences.LONG_SYMBOLIC)
                    || paramType.equals(SymbolicReferences.DOUBLE_SYMBOLIC)) {
                this.argSlotCount++;
            }
        }
        if (!this.isStatic()) {
            this.argSlotCount++;
        }

    }

    public void copyAttributes(MemberInfo memberInfo) {
        CodeAttribute codeAttr = memberInfo.getCodeAttribute();
        if (codeAttr != null){
            this.maxStack = codeAttr.getMaxStack();
            this.maxLocals = codeAttr.getMaxLocals();
            this.code = codeAttr.getCode();
            this.lineNumberTable = codeAttr.getLineNumberTableAttribute();
            this.exceptionTable = new ExceptionTable(codeAttr.getExceptionTable(),
                   this.getClazz().getConstantPool());
        }
    }

    public int findExceptionHandler(Clazz exClazz, int pc)  {
        ExceptionHandler handler = this.exceptionTable.findExceptionHandler(exClazz, pc);
        if (handler != null) {
            return handler.getHandlerPc();
        }
        return -1;
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VOLATILE);
    }

    private boolean isNative() {
        return 0 != (this.accessFlags & AccessFlags.ACC_NATIVE);
    }
    public int getMaxStack() {
        return maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public int getArgSlotCount() {
        return argSlotCount;
    }

    public void setArgSlotCount(int argSlotCount) {
        this.argSlotCount = argSlotCount;
    }

    public byte[] getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Method{" +
                "name='" + name + '\'' +
                ", descriptor='" + descriptor + '\'' +
                '}';
    }

    /**
     * get method line number
     * @param pc
     * @return
     */
    public int getLineNumber(int pc) {
        if (this.isNative()) {
            return -2;
        }
        if (Objects.isNull(this.lineNumberTable)) {
            return -1;
        }
        return this.lineNumberTable.getLineNumber(pc);
    }
}
