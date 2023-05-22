package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.runtime.LocalVars;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 18:46
 */
public class JvmObject {
    private Clazz clazz;
    private LocalVars fields;


    public JvmObject(Clazz clazz) {
        this.clazz = clazz;
        this.fields = new LocalVars(clazz.getInstanceSlotCount());
    }

    public Clazz getClazz() {
        return clazz;
    }

    public LocalVars getFields() {
        return fields;
    }

    public boolean isInstanceOf(Clazz clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }
}
