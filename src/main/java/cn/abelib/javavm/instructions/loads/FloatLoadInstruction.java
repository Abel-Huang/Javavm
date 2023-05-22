package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:36
 */
public interface FloatLoadInstruction {

    default void floatLoad(Frame frame, int index) {
        float val = frame.getLocalVars().getFloat(index);
        frame.getOperandStack().pushFloat(val);
    }
}
