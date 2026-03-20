package cn.abelib.javavm.clazz;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/28 22:54
 */
public class LineNumberTableEntry {
    private int startPc;
    private int lineNumber;

    public LineNumberTableEntry() {
    }

    public LineNumberTableEntry(int startPc, int lineNumber) {
        this.startPc = startPc;
        this.lineNumber = lineNumber;
    }

    public int getStartPc() {
        return startPc;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}
