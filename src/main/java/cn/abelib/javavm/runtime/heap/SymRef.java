package cn.abelib.javavm.runtime.heap;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/23 23:48
 */
public class SymRef {
    protected RuntimeConstantPool constantPool;
    protected String className;
    private Clazz clazz;

    public Clazz resolvedClass() throws IOException {
        if (Objects.isNull(this.clazz)) {
            this.resolveClassRef();
        }
        return this.clazz;
    }

    public void resolveClassRef() throws IOException {
        Clazz clazz = this.constantPool.getClazz();
        Clazz c = clazz.getClassLoader().loadClass(this.className);
        if (!c.isAccessibleTo(clazz)){
            throw new RuntimeException("java.lang.IllegalAccessError");
        }
        this.clazz = c;
    }
}
