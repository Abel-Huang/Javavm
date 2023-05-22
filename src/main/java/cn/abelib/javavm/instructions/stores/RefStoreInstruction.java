package cn.abelib.javavm.instructions.stores;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/3 23:03
 */
public interface RefStoreInstruction {

    default void refStore(Frame frame, int index) {
        JvmObject val = frame.getOperandStack().popRef();
        frame.getLocalVars().setRef(index, val);
    }
}
