package cn.abelib.javavm.clazz;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/28 0:19
 */
public class ExceptionTableEntry {
    private int startPc;

    private int endPc;

    private int handlerPc;

    private int catchType;

    public ExceptionTableEntry(int startPc, int endPc, int handlerPc, int catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }

    public ExceptionTableEntry() {
    }
}
