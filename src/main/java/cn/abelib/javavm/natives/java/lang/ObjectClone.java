package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/4 23:52
 */
public class ObjectClone implements NativeMethod {

    /**
     * public native Object clone();
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();

        Clazz clone;
        try {
            clone = thisRef.getClazz().getClassLoader().loadClass("java/lang/Cloneable");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException");
        }

        if (!thisRef.getClazz().isImplements(clone)) {
            throw new RuntimeException("java.lang.CloneNotSupportedException");
        }

        frame.getOperandStack().pushRef(thisRef.jvmClone());
    }
}
