package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:09
 */
public interface Instruction {
    void fetchOperands(BytecodeReader reader);

    void execute(Frame frame);
}
