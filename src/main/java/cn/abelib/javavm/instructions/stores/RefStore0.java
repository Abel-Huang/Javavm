package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 19:38
 */
public class RefStore0 extends NoOperandsInstruction implements RefStoreInstruction {

    @Override
    public void execute(Frame frame) {
        refStore(frame, 0);
    }
}