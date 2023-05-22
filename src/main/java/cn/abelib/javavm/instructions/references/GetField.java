package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.LocalVars;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 1:12
 */
public class GetField extends Index16Instruction {

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
        if (field.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        OperandStack stack = frame.getOperandStack();
        JvmObject ref = stack.popRef();
        if (Objects.isNull(ref)) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        LocalVars slots = clazz.getStaticVars();

        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                stack.pushInt(slots.getInt(slotId));
            case 'F':
                stack.pushFloat(slots.getFloat(slotId));
            case 'J':
                stack.pushLong(slots.getLong(slotId));
            case 'D':
                stack.pushDouble(slots.getDouble(slotId));
            case 'L':
            case '[':
                stack.pushRef(slots.getRef(slotId));
        }
    }
}