package cn.abelib.javavm.runtime;

import java.util.Stack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:09
 */
public class JavaStack {
    private int maxSize;
    private Frame top;
    private Stack<Frame> stack;

    public JavaStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new Stack<>();
    }

    public void push(Frame frame) {
        if (this.stack.size() >= maxSize) {
            throw new StackOverflowError();
        }
        this.stack.push(frame);
    }
    public Frame pop() {
        if (this.stack.size() == 0) {
            throw new Error("Java Virtual Machine error");
        }
        return this.stack.pop();
    }

    public Frame top() {
        if (this.stack.size() == 0) {
            throw new Error("Java Virtual Machine error");
        }
        return this.stack.peek();
    }
}
