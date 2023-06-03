package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.Slot;
import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/31 0:31
 */
public interface MethodInvokeInstruction {

    default void invokeMethod(Frame frame, Method method) {
        JvmThread thread = frame.getThread();
        Frame newFrame = new Frame(thread, method);
        thread.pushFrame(newFrame);
        int argSlotSlot = method.getArgSlotCount();
        if (argSlotSlot > 0) {
            for (int i = argSlotSlot - 1; i >= 0; i--) {
                Slot slot = frame.getOperandStack().popSlot();
                newFrame.getLocalVars().setSlot(i, slot);
            }
        }
    }
}
