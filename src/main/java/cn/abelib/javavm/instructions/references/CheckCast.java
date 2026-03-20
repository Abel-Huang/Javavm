package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;

import java.io.IOException;
import java.util.Objects;

/**
 * checkcast instruction
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 1:12
 */
public class CheckCast extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        JvmObject ref = stack.popRef();
        stack.pushRef(ref);  // push back, checkcast doesn't change the stack
        
        if (Objects.isNull(ref)) {
            // null can be cast to any reference type
            return;
        }
        
        Method currentMethod = frame.getMethod();
        Clazz currentClass = currentMethod.getClazz();
        RuntimeConstantPool cp = currentClass.getConstantPool();
        ClassRef classRef = cp.getConstant(this.index).getClassRef();
        Clazz clazz;
        try {
            clazz = classRef.resolvedClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang.InstantiationError");
        }
        
        if (!ref.isInstanceOf(clazz)) {
            throw new RuntimeException("java.lang.ClassCastException");
        }
    }
}
