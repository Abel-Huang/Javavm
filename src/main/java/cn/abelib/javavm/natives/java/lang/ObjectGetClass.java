package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
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
        JvmObject jClass = thisRef.getClazz().getJClass();
        frame.getOperandStack().pushRef(jClass);
    }
}
