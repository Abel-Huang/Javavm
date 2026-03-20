package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 19:38
 */
public class RefLoad2 extends NoOperandsInstruction implements RefLoadInstruction {

    @Override
    public void execute(Frame frame) {
        refLoad(frame, 2);
    }
}