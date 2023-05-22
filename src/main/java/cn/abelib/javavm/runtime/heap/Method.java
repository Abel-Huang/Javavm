package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.attributeinfo.CodeAttribute;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/12 23:54
 */
public class Method extends ClassMember {
    private int maxStack;
    private int maxLocals;
    private byte[] code;

    public Method(Clazz clazz, MemberInfo memberInfo) {
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
        this.setClazz(clazz);
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
        return 0 != (this.accessFlags & Const.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & Const.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & Const.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & Const.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & Const.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & Const.ACC_SUPER);
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & Const.ACC_SYNCHRONIZED);
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & Const.ACC_VOLATILE);
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
