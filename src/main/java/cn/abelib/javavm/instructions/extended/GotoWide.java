package cn.abelib.javavm.instructions.extended;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/7 0:00
 */
public class GotoWide extends BranchInstruction {
    private int offset;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readInt32();
    }

    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}
