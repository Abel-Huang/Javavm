package cn.abelib.javavm.runtime;

import java.util.Stack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:09
 */
public class JvmStack {
    private int maxSize;
    private Stack<Frame> stack;

    public JvmStack(int maxSize) {
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
        if (this.isEmpty()) {
            throw new Error("Java Virtual Machine error");
        }
        return this.stack.pop();
    }

    public Frame top() {
        if (this.isEmpty()) {
            throw new Error("Java Virtual Machine error");
        }
        return this.stack.peek();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }
}
