package cn.abelib.javavm.instructions.comparisons;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/5 20:16
 */
public interface DoubleCompare {
    default void compare(Frame frame, boolean flag) {
        OperandStack stack = frame.getOperandStack();
        double v2 = stack.popDouble();
        double v1 = stack.popDouble();
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
