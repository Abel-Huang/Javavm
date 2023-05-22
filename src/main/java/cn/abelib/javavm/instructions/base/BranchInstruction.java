package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:11
 */
public abstract class BranchInstruction implements Instruction {
    /**
     * 存放跳转偏移量
     */
    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.offset = reader.readInt16();
    }

    public void branch(Frame frame, int offset) {
        int pc = frame.getThread().getPc();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }
}
