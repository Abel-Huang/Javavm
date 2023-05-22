package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:49
 */
public class LongStore1 extends NoOperandsInstruction implements LongStoreInstruction {

    @Override
    public void execute(Frame frame) {
        longStore(frame, 1);
    }
}
