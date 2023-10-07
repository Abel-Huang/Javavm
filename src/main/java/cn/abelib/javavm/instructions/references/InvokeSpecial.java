package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.*;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 22:25
 */
public class InvokeSpecial extends Index16Instruction implements MethodInvokeInstruction {

    @Override
    public void execute(Frame frame) {
        Clazz currentClass = frame.getMethod().getClazz();
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = cp.getConstant(this.index).getMethodRef();
        Clazz resolvedClass;
        try {
            resolvedClass = methodRef.resolvedClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }
        Method resolvedMethod;
        try {
            resolvedMethod = methodRef.resolvedMethod();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }

        if (resolvedMethod.getName().equals("<init>")
                && resolvedMethod.getClazz() != resolvedClass) {
            throw new RuntimeException("java.lang.NoSuchMethodError");
        }
        if (resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        // fixme npe
        JvmObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount());
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        Method methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper() &&
                resolvedClass.isSubClassOf(currentClass) &&
                !resolvedMethod.getName().equals("<init>")) {
            methodToBeInvoked = methodRef.lookupMethodInClass(currentClass.getSuperClass(),
                    methodRef.getMethodName(), methodRef.getDescriptor());
        }

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }
        invokeMethod(frame, methodToBeInvoked);
    }
}
