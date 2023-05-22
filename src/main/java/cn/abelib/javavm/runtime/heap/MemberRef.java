package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantMemberRefInfo;
import cn.abelib.javavm.clazz.constantinfo.NameAndType;

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
}
