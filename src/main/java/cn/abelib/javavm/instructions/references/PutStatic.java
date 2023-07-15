package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.LocalVars;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 1:11
 */
public class PutStatic extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        Method currentMethod = frame.getMethod();
        Clazz currentClass = currentMethod.getClazz();
        RuntimeConstantPool cp = currentClass.getConstantPool();
        FieldRef fieldRef = cp.getConstant(this.index).getFieldRef();
        Clazz clazz = null;
        Field field = null;
        try {
            field = fieldRef.resolvedField();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang.InstantiationError");
        }
        clazz = field.getClazz();
        if (!field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        if (field.isFinal()) {
            if (currentClass != clazz || !"<clinit>".equals(currentMethod.getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        LocalVars slots = clazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                slots.setInt(slotId, stack.popInt());
                break;
            case 'F':
                slots.setFloat(slotId, stack.popFloat());
                break;
            case 'J':
                slots.setLong(slotId, stack.popLong());
                break;
            case 'D':
                slots.setDouble(slotId, stack.popDouble());
                break;
            case 'L':
            case '[':
                slots.setRef(slotId, stack.popRef());
        }
    }
}
