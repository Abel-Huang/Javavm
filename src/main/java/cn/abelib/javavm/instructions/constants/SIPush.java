package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 16:28
 * sipush
 */
public class SIPush implements Instruction {
    private int val;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.val = reader.readInt16();
    }

    @Override
    public void execute(Frame frame) {
        int i = this.val;
        frame.getOperandStack().pushInt(i);
    }
}
