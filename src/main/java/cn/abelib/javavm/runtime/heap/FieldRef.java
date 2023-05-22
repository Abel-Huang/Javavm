package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantFieldRefInfo;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/24 22:52
 */
public class FieldRef extends MemberRef {
    private Field field;

    public FieldRef(RuntimeConstantPool cp, ConstantFieldRefInfo refInfo) {
        this.constantPool = cp;
        super.copyMemberRefInfo(refInfo);
    }

    public Field resolvedField() throws IOException {
        if(Objects.isNull( this.field)) {
            this.resolveFieldRef();
        }
        return this.field;
    }

    private void resolveFieldRef() throws IOException {
        Clazz clazz = this.constantPool.getClazz();
        Clazz c = this.resolvedClass();
        Field field = lookupField(c, this.name, this.descriptor);
        if(Objects.isNull(field)) {
            throw new RuntimeException("java.lang.NoSuchFieldError");
        }
        if(!field.isAccessibleTo(clazz))  {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.field = field;
    }

    public Field lookupField(Clazz clazz, String name, String descriptor) {
        for(Field field : clazz.getFields()) {
            if(field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }

        for(Clazz iface : clazz.getInterfaces()) {
            Field field = lookupField(iface, name, descriptor);
            if (field != null) {
                return field;
            }
        }
        if (Objects.nonNull(clazz.getSuperClass())) {
            return lookupField(clazz.getSuperClass(), name, descriptor);
        }
        return null;
    }
}
