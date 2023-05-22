package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:08
 */
public interface FloatCompare {

    default void compare(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        float v2 = stack.popFloat();
        float v1 = stack.popFloat();
        if (v1 > v2) {
            stack.pushInt(1);
        } else if (v1 == v2) {
            stack.pushInt(0);
        } else if (v1 < v2){
            stack.pushInt(-1);
        } else if (flag) {
            stack.pushInt(1);
        }
        stack.pushInt(-1);
    }
}
