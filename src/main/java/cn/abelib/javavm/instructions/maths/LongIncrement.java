package cn.abelib.javavm.instructions.maths;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.LocalVars;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 0:32
 */
public class LongIncrement implements Instruction {
    private int index;
    private int constant;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readInt8();
        this.constant = reader.readInt8();
    }

    @Override
    public void execute(Frame frame) {
        LocalVars localVars = frame.getLocalVars();
        int val = localVars.getInt(this.index);
        val += this.constant;
        localVars.setInt(this.index, val);
    }
}
