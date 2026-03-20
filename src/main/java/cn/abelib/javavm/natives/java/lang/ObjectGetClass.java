package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/25 23:06
 */
public class ObjectGetClass implements NativeMethod {

    /**
     * public final native Class<?> getClass();
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();
        if (thisRef == null) {
            throw new RuntimeException("java.lang.NullPointerException: this is null in getClass()");
        }
        
        Clazz clazz = thisRef.getClazz();
        if (clazz == null) {
            throw new RuntimeException("java.lang.NullPointerException: clazz is null in getClass()");
        }
        
        JvmObject jClass = clazz.getJClass();
        if (jClass == null) {
            throw new RuntimeException("java.lang.NullPointerException: jClass is null for class: " + clazz.getName());
        }
        
        frame.getOperandStack().pushRef(jClass);
    }
}
