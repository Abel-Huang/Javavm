package cn.abelib.javavm.instructions.base;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/2 15:31
 */
public abstract class Index8Instruction implements Instruction {
    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUInt8();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
