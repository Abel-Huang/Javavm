package cn.abelib.javavm.instructions.maths;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 0:26
 */
public class IntegerNegative extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v = stack.popInt();
        stack.pushInt(-v);
    }
}
