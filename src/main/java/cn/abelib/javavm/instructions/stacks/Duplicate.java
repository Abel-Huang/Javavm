package cn.abelib.javavm.instructions.stacks;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.Slot;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 23:30
 * dup指令复制栈顶的单个变量
 */
public class Duplicate extends NoOperandsInstruction {

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        Slot slot = stack.popSlot();
        stack.pushSlot(slot);
        stack.pushSlot(slot);
    }
}