package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:21
 */
public class IfRefCompareEqual extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        JvmObject v2 = stack.popRef();
        JvmObject v1 = stack.popRef();
        if (v1.equals(v2)) {
            branch(frame, this.offset);
        }
    }
}
