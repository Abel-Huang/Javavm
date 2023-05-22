package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 19:38
 */
public class RefStore2 extends Index8Instruction implements RefStoreInstruction {

    @Override
    public void execute(Frame frame) {
        refStore(frame, 2);
    }
}