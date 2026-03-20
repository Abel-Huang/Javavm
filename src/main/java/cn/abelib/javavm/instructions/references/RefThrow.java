package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.StringPool;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/14 23:22
 */
public class RefThrow extends NoOperandsInstruction {
    @Override
    public void execute(Frame frame) {
        JvmObject ex = frame.getOperandStack().popRef();
        if (Objects.isNull(ex)) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
        JvmThread thread = frame.getThread();
        if (!findAndGotoExceptionHandler(thread, ex)) {
            handleUncaughtException(thread, ex);
        }
    }

    private boolean findAndGotoExceptionHandler(JvmThread thread, JvmObject ex) {
        do {
            Frame frame = thread.currentFrame();
            int pc = frame.getNextPc() - 1;
            int handlerPC = frame.getMethod().findExceptionHandler(ex.getClazz(), pc);
            if (handlerPC > 0) {
                OperandStack stack = frame.getOperandStack();
                stack.clear();
                stack.pushRef(ex);
                frame.setNextPC(handlerPC);
                return true;
            }
            thread.popFrame();
        } while (!thread.isStackEmpty());
        return false;
    }

    private void handleUncaughtException(JvmThread thread, JvmObject ex) {
        thread.clearStack();
        JvmObject jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;");
        String goMsg = StringPool.getString(jMsg);
        System.out.println(ex.getClazz().getJvmName() + ": " + goMsg);
        Object stes = ex.getExtra();
        // todo
        String[] setss = new String[]{};
        for (int i = 0; i < setss.length; i++ ){
            String ste = StringUtils.EMPTY;
//            String ste = stes.Index(i).Interface();
            System.out.println("\tat " + ste);
        }
    }
}
