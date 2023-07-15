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
 * @date 2023/5/11 1:11
 */
public class PutField extends Index16Instruction {

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
        if (field.isFinal()) {
            if (currentClass != clazz || !"<clinit>".equals(currentMethod.getName())) {
                throw new RuntimeException("java.lang.IllegalAccessError");
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        LocalVars slots = clazz.getStaticVars();
        OperandStack stack = frame.getOperandStack();
        JvmObject ref = null;
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                int intVal = stack.popInt();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setInt(slotId, intVal);
            case 'F':
                float floatVal = stack.popFloat();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setFloat(slotId, floatVal);
            case 'J':
                long longVal = stack.popLong();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setLong(slotId, longVal);
            case 'D':
                double doubleVal = stack.popDouble();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setDouble(slotId, doubleVal);
            case 'L':
            case '[':
                JvmObject refVal = stack.popRef();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setRef(slotId, refVal);
            default:
                // todo
        }
    }
}