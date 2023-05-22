package cn.abelib.javavm.instructions.controls;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/6 23:48
 */
public class LookupSwitch  extends BranchInstruction {
    private int defaultOffset;
    private int npairs;
    private int[] matchOffsets;

    @Override
    public void execute(Frame frame) {
        int key = frame.getOperandStack().popInt();
        for(int i = 0; i < this.npairs * 2; i += 2) {
            if(this.matchOffsets[i] == key) {
                int offset = this.matchOffsets[i+1];
                branch(frame, offset);
                return;
            }
        }
        branch(frame, this.defaultOffset);
    }

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.npairs = reader.readInt32();
        this.matchOffsets = reader.readInt32s(this.npairs * 2);
    }
}
