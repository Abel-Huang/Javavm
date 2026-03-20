package cn.abelib.javavm.clazz;

import cn.abelib.javavm.runtime.heap.ClassRef;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/15 23:36
 */
public class ExceptionHandler {
    private int startPc;

    private int endPc;

    private int handlerPc;

    private ClassRef catchType;

    public ExceptionHandler(){}

    public ExceptionHandler(int startPc, int endPc, int handlerPc, ClassRef catchType) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchType = catchType;
    }

    public int getStartPc() {
        return startPc;
    }

    public int getEndPc() {
        return endPc;
    }

    public int getHandlerPc() {
        return handlerPc;
    }

    public ClassRef getCatchType() {
        return catchType;
    }
}
