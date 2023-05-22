package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:36
 */
public class DoubleLoad extends Index8Instruction implements DoubleLoadInstruction {

    @Override
    public void execute(Frame frame) {
        doubleLoad(frame, this.index);
    }
}