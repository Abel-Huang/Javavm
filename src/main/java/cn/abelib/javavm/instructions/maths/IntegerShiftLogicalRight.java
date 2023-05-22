package cn.abelib.javavm.instructions.maths;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/4 0:09
 * int逻辑右移
 */
public class IntegerShiftLogicalRight extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        int s = v2 & 0x1f;
        int result = v1 >>> s;
        stack.pushInt(result);
    }
}
