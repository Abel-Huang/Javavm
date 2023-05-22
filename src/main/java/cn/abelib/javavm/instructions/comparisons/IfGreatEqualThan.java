package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:44
 */
public class IfGreatEqualThan extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        int val = frame.getOperandStack().popInt();
        if (val >= 0) {
            branch(frame, this.offset);
        }
    }
}
