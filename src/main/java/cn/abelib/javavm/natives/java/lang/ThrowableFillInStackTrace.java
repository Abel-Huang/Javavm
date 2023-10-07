package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.natives.StackTraceElement;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.Method;

import java.util.Arrays;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/14 23:07
 */
public class ThrowableFillInStackTrace implements NativeMethod {

    @Override
    public void execute(Frame frame) {
        JvmObject thisObject = frame.getLocalVars().getThis();
        frame.getOperandStack().pushRef(thisObject);
        Object stes = createStackTraceElements(thisObject, frame.getThread());
        thisObject.setExtra(stes);
    }

    private Object createStackTraceElements(JvmObject thisObject, JvmThread thread) {
        int skip = distanceToObject(thisObject.getClazz()) + 2;
        // todo skip array item
        Frame[] frames = Arrays.copyOf(thread.getFrames(),thread.getFrames().length - skip);
        StackTraceElement[] stes = new StackTraceElement[frames.length];
        for(int i = 0; i < frames.length; i ++)  {
            Frame frame = frames[i];
            stes[i] = createStackTraceElement(frame);
        }
        return stes;
    }

    /**
     * calc class parent class level
     * @param clazz
     * @return
     */
    private int distanceToObject(Clazz clazz) {
        int distance = 0;
        for(Clazz c = clazz.getSuperClass(); c != null; c = c.getSuperClass())  {
            distance++;
        }
        return distance;
    }

    private StackTraceElement createStackTraceElement(Frame frame) {
        Method method = frame.getMethod();
        Clazz clazz = method.getClazz();
        return new StackTraceElement(clazz.getSourceFile(),
                clazz.getJvmName(),
                method.getName(),
                method.getLineNumber(frame.getNextPc() - 1));
    }
}
