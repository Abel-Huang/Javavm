package cn.abelib.javavm.instructions.controls;

import cn.abelib.javavm.instructions.base.BranchInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/6 23:24
 */
public class Goto extends BranchInstruction {
    @Override
    public void execute(Frame frame) {
        branch(frame, this.offset);
    }
}
