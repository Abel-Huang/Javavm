package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantMethodRefInfo;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/24 22:57
 */
public class MethodRef extends MemberRef{
    private Method method;

    public MethodRef(RuntimeConstantPool cp, ConstantMethodRefInfo refInfo){
        this.constantPool = cp;
        super.copyMemberRefInfo(refInfo);
    }

    public Method resolvedMethod() throws IOException {
        if (Objects.isNull(this.method)) {
            this.resolveMethodRef();
        }
        return this.method;
    }

    private void resolveMethodRef() throws IOException {
        Clazz d = this.constantPool.getClazz();
        Clazz c = this.resolvedClass();
        if (c.isInterface()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        Method method = lookupMethod(c, this.name, this.descriptor);
        if (Objects.isNull(this.method)) {
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }
        if (!method.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.method = method;
    }

    private Method lookupMethod(Clazz c, String name, String descriptor) {
        Method method = lookupMethodInClass(c, name, descriptor);
        if (Objects.isNull(this.method)) {
            method = lookupMethodInInterfaces(c.getInterfaces(), name, descriptor);
        }
        return method;
    }

    public Method lookupMethodInClass(Clazz c, String name, String descriptor) {
        for (Clazz clazz  = c; clazz != null; clazz = clazz.getSuperClass()) {
            for(Method method : clazz.getMethods()) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    public String getMethodName() {
        return this.name;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
