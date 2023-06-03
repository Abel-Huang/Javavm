package cn.abelib.javavm.runtime.heap;


import cn.abelib.javavm.clazz.MemberInfo;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/13 23:15
 */
public class ClassMember {
    protected int accessFlags;
    protected String name;
    protected String descriptor;
    private Clazz clazz;

    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.getAccessFlags();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public String getDescriptor() {
        return descriptor;
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

    public boolean isAccessibleTo(Clazz d) {
        if (this.isPublic()) {
            return true;
        }
        Clazz c = this.getClazz();
        if(this.isProtected()) {
            return d == c || d.isSubClassOf(c) ||
                    c.getPackageName().equals(d.getPackageName());
        }
        if(!this.isPrivate()) {
            return c.getPackageName().equals(d.getPackageName());
        }
        return d == c;
    }
}
