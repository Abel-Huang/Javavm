package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:21
 */
public class IfIntegerCompareLowEqual extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int v2 = stack.popInt();
        int v1 = stack.popInt();
        if (v1 < v2) {
            branch(frame, this.offset);
        }
    }
}
