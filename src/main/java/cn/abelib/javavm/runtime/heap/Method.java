package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.attributeinfo.CodeAttribute;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

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

    public Method(Clazz clazz, MemberInfo memberInfo) {
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
        this.setClazz(clazz);
        this.calcArgSlotCount();
    }

    private void calcArgSlotCount() {
        MethodDescriptor parsedDescriptor = new MethodDescriptor() ;
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
        }
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
}
