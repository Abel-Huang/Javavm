package cn.abelib.javavm.runtime;

import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:06
 */
public class Frame {
    private Frame lower;
    private LocalVars localVars;
    private OperandStack operandStack;
    private JavaThread thread;
    private Method method;
    private int nextPc;

    public Frame(JavaThread thread, int maxLocals, int maxStack) {
        this.thread = thread;
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public Frame(JavaThread thread, Method method) {
        this.thread = thread;
        this.localVars = new LocalVars(method.getMaxLocals());
        this.operandStack = new OperandStack(method.getMaxStack());
        this.method = method;
    }

    public Frame(int maxLocals, int maxStack) {
        this(null, maxLocals, maxStack);
    }

    public Frame getLower() {
        return lower;
    }

    public LocalVars getLocalVars() {
        return localVars;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }

    public void setNextPC(int nextPC) {
        this.nextPc = nextPC;
    }

    public int getNextPc() {
        return nextPc;
    }

    /**
     * @return
     */
    public JavaThread getThread() {
        return this.thread;
    }

    public Method getMethod() {
        return method;
    }
}
