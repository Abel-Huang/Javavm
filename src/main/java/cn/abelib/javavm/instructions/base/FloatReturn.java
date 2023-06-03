package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/1 0:26
 */
public class FloatReturn extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        JvmThread thread = frame.getThread();
        Frame currentFrame = thread.popFrame();
        Frame invokerFrame = thread.topFrame();
        float retVal = currentFrame.getOperandStack().popFloat();
        invokerFrame.getOperandStack().pushFloat(retVal);
    }
}
