package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/25 23:13
 */
public class ClazzSetOut0 implements NativeMethod {

    /**
     * private static native void setOut0(PrintStream out);
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        JvmObject out = frame.getLocalVars().getRef(0);
        Clazz sysClass = frame.getMethod().getClazz();
        sysClass.setRefVar("out", "Ljava/io/PrintStream;", out);
    }
}
