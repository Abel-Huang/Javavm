package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/6 23:26
 */
public class InitClazz {

    // jvms 5.5
    public static void initClass(JvmThread thread, Clazz clazz) {
        clazz.setInitStarted(true);
        scheduleClinit(thread, clazz);
        initSuperClass(thread, clazz);
    }

    private static void scheduleClinit(JvmThread thread, Clazz clazz) {
        Method clinit = clazz.getClInitMethod();
        if (clinit != null && clinit.getClazz() == clazz) {
            // exec <clinit>
            Frame newFrame = new Frame(thread, clinit);
            thread.pushFrame(newFrame);
        }
    }

    private static void initSuperClass(JvmThread thread, Clazz clazz) {
        if (!clazz.isInterface()) {
            Clazz superClass = clazz.getSuperClass();
            if (superClass != null && !superClass.isInitStarted()) {
                initClass(thread, superClass);
            }
        }
    }
}
