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
 * @date 2023/7/10 22:49
 */
public class DoubleArrayStore extends NoOperandsInstruction implements ArrayInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        double val = stack.popDouble();
        int index = stack.popInt();
        JvmObject arrRef = stack.popRef();
        checkNotNil(arrRef);
        List<Double> doubles = arrRef.getDoubles();
        checkIndex(doubles.size(), index);
        doubles.set(index, val);
    }
}