package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JvmThread;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/7 0:32
 */
public class Interpreter {

    public static void interpret(Method method, boolean printLog) {
        // 其他代码
        JvmThread thread = new JvmThread();
        Frame frame = new Frame(thread, method);
        try {
            thread.pushFrame(frame);
            loop(thread, printLog);
        } catch (Exception e) {
            e.printStackTrace();
            // todo System.out.printf
            System.out.printf("LocalVars: %s", frame.getLocalVars());
            System.out.printf("OperandStack: %s", frame.getOperandStack());
        }
    }

    private static void loop(JvmThread thread, boolean printLog) {

        BytecodeReader reader = new BytecodeReader();
        while (true) {
            Frame frame = thread.popFrame();
            int pc = frame.getNextPc();
            thread.setPc(pc);
            // decode
            reader.reset(frame.getMethod().getCode(), pc);
            int opcode = reader.readUInt8();
            Instruction inst = Instructions.newInstruction(opcode);
            inst.fetchOperands(reader);
            frame.setNextPC(reader.getPc());

            if (printLog) {
                System.out.printf("pc:%2d inst:%s %s", pc, inst, frame.getLocalVars());
                OperandStack stack = frame.getOperandStack();
                System.out.printf("stack %s", stack.toString());
            }

            // execute
            inst.execute(frame);
            if (thread.isStackEmpty()) {
                break;
            }
        }
    }
}
