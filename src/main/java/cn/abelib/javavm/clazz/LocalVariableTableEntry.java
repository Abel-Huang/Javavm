package cn.abelib.javavm.clazz;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/12 0:25
 */
public class LocalVariableTableEntry {
    private int startPc;
    private int length;
    private int nameIndex;
    private int descriptorIndex;
    private int index;

    public LocalVariableTableEntry() {
    }

    public LocalVariableTableEntry(int startPc, int length, int nameIndex, int descriptorIndex, int index) {
        this.startPc = startPc;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }
}
