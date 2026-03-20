package cn.abelib.javavm.instructions.base;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:33
 */
public abstract class Index16Instruction implements Instruction {
    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUInt16();
    }
}
