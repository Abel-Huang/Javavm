package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.StringPool;

/**
 * Class.initClassName() native method
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/5
 */
public class ClazzInitClassName implements NativeMethod {

    /**
     * private native String initClassName();
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();
        if (thisRef == null) {
            throw new RuntimeException("java.lang.NullPointerException: this is null in initClassName()");
        }
        
        Clazz clazz = (Clazz) thisRef.getExtra();
        if (clazz == null) {
            throw new RuntimeException("java.lang.NullPointerException: extra is null in initClassName()");
        }
        
        String name = clazz.getJvmName();
        JvmObject nameObj = StringPool.getString(clazz.getClassLoader(), name);
        frame.getOperandStack().pushRef(nameObj);
    }
}
