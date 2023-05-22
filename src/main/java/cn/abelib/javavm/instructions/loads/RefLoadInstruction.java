package cn.abelib.javavm.instructions.loads;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 23:03
 */
public interface RefLoadInstruction {

    default void refLoad(Frame frame, int index) {
        JvmObject val = frame.getLocalVars().getRef(index);
        frame.getOperandStack().pushRef(val);
    }
}
