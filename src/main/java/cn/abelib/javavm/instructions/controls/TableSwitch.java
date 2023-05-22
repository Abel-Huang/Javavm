package cn.abelib.javavm.instructions.controls;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/6 23:28
 */
public class TableSwitch extends BranchInstruction {
    private int defaultOffset;
    private int low;
    private int high;
    private int[] jumpOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        this.defaultOffset = reader.readInt32();
        this.low = reader.readInt32();
        this.high = reader.readInt32();
        int jumpOffsetsCount = this.high - this.low + 1;
        this.jumpOffsets = reader.readInt32s(jumpOffsetsCount);
    }

    @Override
    public void execute(Frame frame) {
        int index = frame.getOperandStack().popInt();
        int offset;
        if (index >= this.low && index <= this.high) {
            offset = this.jumpOffsets[index -  this.low];
        } else {
            offset = (this.defaultOffset);
        }
        branch(frame, offset);
    }
}
