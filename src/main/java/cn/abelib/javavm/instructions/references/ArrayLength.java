package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/24 21:48
 */
public class ArrayLength extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        JvmObject ref = stack.popRef();
        if (Objects.isNull(ref)) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
        int arrLen = ref.getArrayLength();
        stack.pushInt(arrLen);
    }
}
