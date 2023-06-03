package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/1 0:20
 */
public class Return extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        frame.getThread().popFrame();
    }
}
