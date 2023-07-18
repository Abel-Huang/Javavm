package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:32
 */
public interface LoadConstantInstruction {
    default void ldc(Frame frame, int index) {
        OperandStack stack = frame.getOperandStack();
        Clazz clazz = frame.getMethod().getClazz();
        RuntimeConstantPool cp = clazz.getConstantPool();
        RuntimeConstantPoolInfo poolInfo = cp.getConstant(index);
        if (poolInfo.isSetIntValue()) {
            stack.pushInt(poolInfo.getIntValue());
            return;
        } else if (poolInfo.isSetFloatValue()) {
            stack.pushFloat(poolInfo.getFloatValue());
            return;
        } else if (poolInfo.isSetStringValue()) {
            // support string
            JvmObject strRef = StringPool.getString(clazz.getClassLoader(), poolInfo.getStringValue());
            stack.pushRef(strRef);
            return;
        } else if (poolInfo.isSetClassRef()) {
            // todo
            return;
        }
        throw new RuntimeException("todo: ldc!");
    }
}
