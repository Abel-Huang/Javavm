package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:49
 * 存储指令把变量从操作数栈顶弹出，
 * 然后存入局部变量表
 */
public class LongStore extends Index8Instruction implements LongStoreInstruction {

    @Override
    public void execute(Frame frame) {
        longStore(frame, this.index);
    }
}
