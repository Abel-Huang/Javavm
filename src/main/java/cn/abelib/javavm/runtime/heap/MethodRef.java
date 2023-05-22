package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantMethodRefInfo;

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

    public String getMethodName() {
        return method.getName();
    }

    public String getDescriptor() {
        return method.getDescriptor();
    }
}
