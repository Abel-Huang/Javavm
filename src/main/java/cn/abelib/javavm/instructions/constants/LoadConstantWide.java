package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPoolInfo;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:25
 */
public class LoadConstantWide extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        RuntimeConstantPoolInfo poolInfo = cp.getConstant(index);
        if (poolInfo.isSetLongValue()) {
            stack.pushLong(poolInfo.getLongValue());
        } else if (poolInfo.isSetDoubleValue()) {
            stack.pushDouble(poolInfo.getDoubleValue());
        } else if (poolInfo.isSetStringValue()) {
            // todo
        } else if (poolInfo.isSetClassRef()) {
            // todo
        }
        throw new RuntimeException("todo: ldc!");
    }
}
