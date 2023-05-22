package cn.abelib.javavm.instructions.base;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:10
 */
public abstract class NoOperandsInstruction implements Instruction {
    @Override
    public void fetchOperands(BytecodeReader reader) {
        // do nothing
    }
}
