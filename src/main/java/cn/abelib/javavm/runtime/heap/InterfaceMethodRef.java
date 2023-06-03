package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantInterfaceMethodRefInfo;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/24 23:00
 */
public class InterfaceMethodRef extends MemberRef{
    private Method method;

    public InterfaceMethodRef(RuntimeConstantPool cp, ConstantInterfaceMethodRefInfo refInfo){
        this.constantPool = cp;
        super.copyMemberRefInfo(refInfo);
    }

    public Method ResolvedInterfaceMethod() throws IOException {
        if (Objects.isNull(this.method)) {
            this.resolveInterfaceMethodRef();
        }
        return this.method;
    }

    public void resolveInterfaceMethodRef() throws IOException {
        Clazz d = this.constantPool.getClazz();
        Clazz c = this.resolvedClass();
        if (!c.isInterface()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        Method method = lookupInterfaceMethod(c, this.name, this.descriptor);
        if (Objects.isNull(this.method)) {
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }
        if (!method.isAccessibleTo(d)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.method = method;
    }

    private Method lookupInterfaceMethod(Clazz iface, String name, String descriptor) {
        for(Method method : iface.getMethods()) {
            if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                return method;
            }
        }
        return lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
    }
}