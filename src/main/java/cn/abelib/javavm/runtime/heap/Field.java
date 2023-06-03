package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.attributeinfo.ConstantValueAttribute;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/20 0:01
 */
public class Field extends ClassMember {
    private int slotId;
    private int constValueIndex;

    public Field(Clazz clazz, MemberInfo memberInfo) {
        this.copyMemberInfo(memberInfo);
        this.copyAttributes(memberInfo);
        this.setClazz(clazz);
    }

    public void copyAttributes(MemberInfo memberInfo) {
        ConstantValueAttribute valAttr = memberInfo.getConstantValueAttribute();
        if (Objects.nonNull(valAttr)) {
            this.constValueIndex = valAttr.getConstantValueIndex();
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

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public int getConstValueIndex() {
        return constValueIndex;
    }

    public void setConstValueIndex(int constValueIndex) {
        this.constValueIndex = constValueIndex;
    }

    public boolean isLongOrDouble() {
        return "J".equals(this.descriptor) || "D".equals(this.descriptor);
    }
}
