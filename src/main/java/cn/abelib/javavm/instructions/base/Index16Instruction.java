package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:33
 */
public class Index16Instruction implements Instruction {
    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUInt16();
    }

    @Override
    public void execute(Frame frame) {

    }
}
