package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 19:39
 */
public class IntegerStore1 extends NoOperandsInstruction implements IntegerStoreInstruction {

    @Override
    public void execute(Frame frame) {
        intStore(frame, 1);
    }
}
