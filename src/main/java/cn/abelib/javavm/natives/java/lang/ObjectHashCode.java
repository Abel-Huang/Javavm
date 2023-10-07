package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/4 23:53
 */
public class ObjectHashCode implements NativeMethod {

    /**
     * public native int hashCode();
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();
        frame.getOperandStack().pushInt(thisRef.hashCode());
    }
}
