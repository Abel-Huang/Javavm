package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantMemberRefInfo;
import cn.abelib.javavm.clazz.constantinfo.NameAndType;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/24 22:47
 */
public class MemberRef extends SymRef {

    protected String name;
    protected String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo refInfo) {
        this.className = refInfo.getClassName();
        NameAndType nameAndType = refInfo.getNameAndDescriptor();
        this.name = nameAndType.getName();
        this.descriptor = nameAndType.getType();
    }

    protected Method lookupMethodInInterfaces(Clazz[] interfaces, String name, String descriptor) {
        if (ArrayUtils.isEmpty(interfaces)) {
            return null;
        }
        for (Clazz iface : interfaces) {
            for(Method method : iface.getMethods()) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
            Method method = lookupMethodInInterfaces(iface.getInterfaces(), name, descriptor);
            if (Objects.nonNull(method)) {
                return method;
            }
        }
        return null;
    }
}
