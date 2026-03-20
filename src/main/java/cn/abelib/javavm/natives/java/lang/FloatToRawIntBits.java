package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/4 23:32
 */
public class FloatToRawIntBits implements NativeMethod {

    public FloatToRawIntBits() {

    }

    @Override
    public void execute(Frame frame) {
        float value = frame.getLocalVars().getFloat(0);
        frame.getOperandStack().pushInt(Float.floatToIntBits(value));
    }
}
