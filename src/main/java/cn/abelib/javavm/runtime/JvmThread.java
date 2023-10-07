package cn.abelib.javavm.runtime;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 18:47
 */
public class JvmThread {
    private int pc;

    private JvmStack stack;

    public int getPc() {
        return this.pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public JvmThread() {
        stack = new JvmStack(1024);
    }

    public void pushFrame(Frame frame) {
        this.stack.push(frame);
    }

    public Frame popFrame() {
        return this.stack.pop();
    }

    public Frame topFrame() {
        return this.stack.top();
    }

    /**
     * alias for topFrame
     * @return
     */
    public Frame currentFrame() {
        return this.topFrame();
    }

    public boolean isStackEmpty() {
        return this.stack.isEmpty();
    }

    public JvmStack getStack() {
        return this.stack;
    }

    public void clearStack() {
        this.stack.clear();
    }

    public Frame[] getFrames() {
        return this.stack.getFrames();
    }
}
