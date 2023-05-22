package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.instructions.base.Index8Instruction;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 22:33
 * ILOAD
 * 加载指令从局部变量表获取变量，
 * 然后推入操作数栈顶
 */
public class LongLoad2 extends Index8Instruction implements LongLoadInstruction {

    @Override
    public void execute(Frame frame) {
        longLoad(frame, 2);
    }
}
