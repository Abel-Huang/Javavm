package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.StringPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/4 23:58
 */
public class ClazzGetName0 implements NativeMethod {

    /**
     * private native String getName0();
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();
        Clazz extra = (Clazz) thisRef.getExtra();
        String name = extra.getJvmName();
        JvmObject nameObj = StringPool.getString(extra.getClassLoader(), name);
        frame.getOperandStack().pushRef(nameObj);
    }
}
