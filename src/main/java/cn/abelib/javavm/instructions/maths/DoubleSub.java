package cn.abelib.javavm.instructions.maths;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/4 23:51
 */
public class DoubleSub extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
        double result = v1 - v2;
        stack.pushDouble(result);
    }
}
