package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:36
 */
public interface IntegerLoadInstruction {

    default void integerLoad(Frame frame, int index) {
        int val = frame.getLocalVars().getInt(index);
        frame.getOperandStack().pushInt(val);
    }
}
