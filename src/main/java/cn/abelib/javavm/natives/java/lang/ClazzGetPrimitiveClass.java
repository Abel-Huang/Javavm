package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.ClassLoader;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.StringPool;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/4 23:56
 */
public class ClazzGetPrimitiveClass implements NativeMethod {

    /**
     * static native Class<?> getPrimitiveClass(String name);
     * @return
     */
    @Override
    public void execute(Frame frame) {
        JvmObject nameObj = frame.getLocalVars().getThis();
        String name = StringPool.getString(nameObj);
        ClassLoader loader = frame.getMethod().getClazz().getClassLoader();
        JvmObject clazz;
        try {
            clazz = loader.loadClass(name).getJClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException");
        }
        frame.getOperandStack().pushRef(clazz);
    }
}
