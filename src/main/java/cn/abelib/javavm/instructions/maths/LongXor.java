package cn.abelib.javavm.instructions.maths;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/4 0:26
 */
public class LongXor extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long v2 = stack.popLong();
        long v1 = stack.popLong();
        long result = v1 ^ v2;
        stack.pushLong(result);
    }
}
