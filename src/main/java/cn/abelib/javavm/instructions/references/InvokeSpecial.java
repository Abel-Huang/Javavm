package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 22:25
 */
public class InvokeSpecial extends Index16Instruction  {

    @Override
    public void execute(Frame frame) {
        // todo
        frame.getOperandStack().popRef();
    }
}
