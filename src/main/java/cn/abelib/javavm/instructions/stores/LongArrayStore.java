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
public class LongArrayStore extends NoOperandsInstruction implements ArrayInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        long val = stack.popLong();
        int index = stack.popInt();
        JvmObject arrRef = stack.popRef();
        checkNotNil(arrRef);
        List<Long> longs = arrRef.getLongs();
        checkIndex(longs.size(), index);
        longs.set(index, val);
    }
}