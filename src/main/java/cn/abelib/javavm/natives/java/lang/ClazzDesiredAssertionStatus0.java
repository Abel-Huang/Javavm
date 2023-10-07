package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/5 0:01
 */
public class ClazzDesiredAssertionStatus0 implements NativeMethod {
    @Override
    public void execute(Frame frame) {
        frame.getOperandStack().pushBoolean(false);
    }
}
