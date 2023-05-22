package cn.abelib.javavm.instructions.extended;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/7 0:22
 */
public class IfNonNull extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        JvmObject ref = frame.getOperandStack().popRef();
        if (Objects.nonNull(ref)) {
            branch(frame, this.offset);
        }
    }
}
