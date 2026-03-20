package cn.abelib.javavm.natives.java.io;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.StringPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/21 0:00
 */
public class FileOutputStreamIntern implements NativeMethod {

    /**
     * public final native Class<?> getClass();
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        JvmObject thisRef = frame.getLocalVars().getThis();
        // todo Intern
        JvmObject value = StringPool.getString(frame.getMethod().getClazz().getClassLoader(), String.valueOf(thisRef));
        frame.getOperandStack().pushRef(value);
    }
}
