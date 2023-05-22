package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.JavaThread;
import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/7 0:32
 */
public class Interpreter {

    public static void interpret(Method method) {
        // 其他代码
        JavaThread thread = new JavaThread();
        Frame frame = new Frame(thread, method);
        try {
            thread.pushFrame(frame);
            loop(thread, method.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(String.format("LocalVars: %s", frame.getLocalVars()));
            System.out.println(String.format("OperandStack: %s", frame.getOperandStack()));
        }
    }

    private static void loop(JavaThread thread, byte[] bytecode) {
        Frame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader();
        while (true) {
            int pc = frame.getNextPc();
            thread.setPc(pc);
            // decode
            reader.reset(bytecode, pc);
            int opcode = reader.readUInt8();
            Instruction inst = Instructions.newInstruction(opcode);
            inst.fetchOperands(reader);
            frame.setNextPC(reader.getPc());
            // execute
            System.out.println(String.format("pc:%2d inst:%s %s", pc, inst, frame.getLocalVars()));
            inst.execute(frame);
        }
    }
}
