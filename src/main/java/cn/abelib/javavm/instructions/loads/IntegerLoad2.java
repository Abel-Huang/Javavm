package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:33
 * ILOAD_2
 */
public class IntegerLoad2 extends NoOperandsInstruction implements IntegerLoadInstruction {

    @Override
    public void execute(Frame frame) {
        integerLoad(frame, 2);
    }
}
