package cn.abelib.javavm.natives;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/17 1:02
 * record JVM stack frame info
 */
public class StackTraceElement {
    /**
     * filename of class
     */
    private String fileName;

    private String className;

    private String methodName;

    /**
     * execute line for current frame
     */
    private int lineNumber;

    public StackTraceElement(){}

    public StackTraceElement(String fileName, String className, String methodName, int lineNumber) {
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }
}
