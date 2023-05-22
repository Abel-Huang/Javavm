package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:26
 */
public class LoadConstant2Wide extends Index16Instruction implements LoadConstantInstruction {
    @Override
    public void execute(Frame frame) {
        ldc(frame, this.index);
    }
}

