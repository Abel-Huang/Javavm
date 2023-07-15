package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.instructions.base.ArrayInstruction;
import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/10 22:40
 */
public class ShortArrayStore extends NoOperandsInstruction implements ArrayInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int val = stack.popInt();
        int index = stack.popInt();
        JvmObject arrRef = stack.popRef();
        checkNotNil(arrRef);
        List<Integer> shorts = arrRef.getShorts();
        checkIndex(shorts.size(), index);
        shorts.set(index, val);
    }
}