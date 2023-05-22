package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:17
 */
public class LoadConstant extends Index8Instruction implements LoadConstantInstruction {
    @Override
    public void execute(Frame frame) {
        ldc(frame, this.index);
    }
}
