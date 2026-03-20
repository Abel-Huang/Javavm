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
        // 简化 final 字段检查 - 允许在构造函数中设置 final 实例字段
        if (field.isFinal()) {
            // 允许：<clinit> 中设置静态 final 字段，或 <init> 中设置实例 final 字段
            boolean isStatic = field.isStatic();
            boolean isClinit = "<clinit>".equals(currentMethod.getName());
            boolean isInit = "<init>".equals(currentMethod.getName());
            
            if (isStatic && !isClinit) {
                // 静态 final 字段只能在 <clinit> 中设置
                throw new RuntimeException("java.lang.IllegalAccessError: cannot set static final field outside <clinit>");
            }
            // 实例 final 字段可以在 <init> 中设置（简化处理，不检查是否是定义该字段的类）
            if (!isStatic && !isInit && !isClinit) {
                throw new RuntimeException("java.lang.IllegalAccessError: cannot set final field outside <init> or <clinit>");
            }
        }

        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
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
                break;
            case 'F':
                float floatVal = stack.popFloat();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setFloat(slotId, floatVal);
                break;
            case 'J':
                long longVal = stack.popLong();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setLong(slotId, longVal);
                break;
            case 'D':
                double doubleVal = stack.popDouble();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setDouble(slotId, doubleVal);
                break;
            case 'L':
            case '[':
                JvmObject refVal = stack.popRef();
                ref = stack.popRef();
                if (Objects.isNull(ref)) {
                    throw new RuntimeException("java.lang.NullPointerException");
                }
                ref.getData().setRef(slotId, refVal);
                break;
            default:
                throw new RuntimeException("Unknown field descriptor: " + descriptor);
        }
    }
}