package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.ClassLoader;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.io.IOException;

import static cn.abelib.javavm.instructions.references.ArrayTypeConstants.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/24 19:29
 */
public class NewArray implements Instruction {
    private int atype;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.atype = reader.readUInt8();
    }

    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new RuntimeException("java.lang.NegativeArraySizeException");
        }
        ClassLoader classLoader = frame.getMethod().getClazz().getClassLoader();
        Clazz arrClass;
        try {
            arrClass = getPrimitiveArrayClass(classLoader, this.atype);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("getPrimitiveArrayClass exception");
        }
        JvmObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }

    private Clazz getPrimitiveArrayClass(ClassLoader classLoader, int atype) throws IOException {
        switch (atype) {
            case AT_BOOLEAN:
                return classLoader.loadClass("[Z");
            case AT_BYTE:
                return classLoader.loadClass("[B");
            case AT_CHAR:
                return classLoader.loadClass("[C");
            case AT_SHORT:
                return classLoader.loadClass("[S");
            case AT_INT:
                return classLoader.loadClass("[I");
            case AT_LONG:
                return classLoader.loadClass("[J");
            case AT_FLOAT:
                return classLoader.loadClass("[F");
            case AT_DOUBLE:
                return classLoader.loadClass("[D");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }
}
