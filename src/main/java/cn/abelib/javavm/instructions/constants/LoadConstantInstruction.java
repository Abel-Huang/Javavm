package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPoolInfo;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:32
 */
public interface LoadConstantInstruction {
    default void ldc(Frame frame, int index) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        RuntimeConstantPoolInfo poolInfo = cp.getConstant(index);
        if (poolInfo.isSetIntValue()) {
            stack.pushInt(poolInfo.getIntValue());
            return;
        } else if (poolInfo.isSetFloatValue()) {
            stack.pushFloat(poolInfo.getFloatValue());
            return;
        } else if (poolInfo.isSetStringValue()) {
            // todo
            return;
        } else if (poolInfo.isSetClassRef()) {
            // todo
            return;
        }
        throw new RuntimeException("todo: ldc!");
    }
}
