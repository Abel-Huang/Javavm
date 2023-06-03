package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/1 0:20
 */
public class RefReturn extends NoOperandsInstruction{
    @Override
    public void execute(Frame frame) {
        JvmThread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        JvmObject retVal = currentFrame.getOperandStack().popRef();
        invokerFrame.getOperandStack().pushRef(retVal);
    }
}
