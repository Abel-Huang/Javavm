package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.ClassRef;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/24 20:16
 */
public class RefNewArray extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = cp.getConstant(this.index).getClassRef();
        // array component class
        Clazz componentClass;
        try {
            componentClass = classRef.resolvedClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }
        OperandStack stack = frame.getOperandStack();
        int count = stack.popInt();
        if (count < 0) {
            throw new RuntimeException("java.lang.NegativeArraySizeException");
        }
        Clazz arrClass;
        try {
            arrClass = componentClass.arrayClass();
        } catch (IOException e) {
            throw new RuntimeException("java.io.IOException");
        }
        JvmObject arr = arrClass.newArray(count);
        stack.pushRef(arr);
    }
}