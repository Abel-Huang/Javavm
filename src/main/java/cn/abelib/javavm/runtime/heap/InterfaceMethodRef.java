package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantInterfaceMethodRefInfo;

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
}