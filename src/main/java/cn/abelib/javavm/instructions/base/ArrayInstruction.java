package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/10 22:21
 */
public interface ArrayInstruction {
    default void checkNotNil(JvmObject arrRef) {
        if (Objects.isNull(arrRef)) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
    }

    default void checkIndex(int arrLen, int index) {
        if (index < 0 || index >= arrLen) {
            throw new RuntimeException("ArrayIndexOutOfBoundsException");
        }
    }
}
