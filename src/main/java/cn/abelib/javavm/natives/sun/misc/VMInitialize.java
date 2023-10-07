package cn.abelib.javavm.natives.sun.misc;

import cn.abelib.javavm.instructions.references.MethodInvokeInstruction;
import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.ClassLoader;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.Method;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/10 23:13
 */
public class VMInitialize implements NativeMethod {

    private static MethodInvokeInstruction anonymousMethod = new MethodInvokeInstruction(){

    };

    @Override
    public void execute(Frame frame) {
        ClassLoader classLoader = frame.getMethod().getClazz().getClassLoader();

        try {
            Clazz jlSysClass = classLoader.loadClass("java/lang/System");
            Method initSysClass = jlSysClass.getStaticMethod("initializeSystemClass", "()V");
            anonymousMethod.invokeMethod(frame, initSysClass);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("IOException");
        }
    }
}
