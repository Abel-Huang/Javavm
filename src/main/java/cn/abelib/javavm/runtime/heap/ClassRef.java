package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.constantinfo.ConstantClassInfo;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/23 23:56
 */
public class ClassRef extends SymRef {

    public ClassRef(RuntimeConstantPool constantPool, ConstantClassInfo constantInfo) {
        this.constantPool = constantPool;
        this.className = constantInfo.name();
    }
}
