package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 22:29
 */
public class InvokeVirtual extends Index16Instruction implements MethodInvokeInstruction {

    @Override
    public void execute(Frame frame) {
        Clazz currentClass = frame.getMethod().getClazz();
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = cp.getConstant(this.index).getMethodRef();

        Method resolvedMethod;
        try {
            resolvedMethod = methodRef.resolvedMethod();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }
        if (resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }
        JvmObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            // hack!
            if (methodRef.getMethodName().equals("println")) {
                _println(frame.getOperandStack(), methodRef.getDescriptor());
                return;
            }
            throw new RuntimeException("java.lang.NullPointerException");
        }

        if (resolvedMethod.isProtected() &&
                resolvedMethod.getClazz().isSubClassOf(currentClass) &&
                !resolvedMethod.getClazz().getPackageName().equals(currentClass.getPackageName()) &&
                ref.getClazz() != currentClass &&
                !ref.getClazz().isSubClassOf(currentClass)) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        Method methodToBeInvoked = methodRef.lookupMethodInClass(currentClass.getSuperClass(),
                methodRef.getMethodName(), methodRef.getDescriptor());

        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }
        invokeMethod(frame, methodToBeInvoked);
    }

    private void _println(OperandStack stack, String descriptor) {
        switch (descriptor) {
            case "(Z)V":
                System.err.printf("%d%n", stack.popInt() != 0);
                break;
            case "(C)V":
            case "(B)V":
            case "(S)V":
            case "(I)V":
                System.err.printf("%d%n", stack.popInt());
                break;
            case "(J)V":
                System.err.printf("%d%n", stack.popLong());
                break;
            case "(F)V":
                System.err.printf("%f%n", stack.popFloat());
                break;
            case "(D)V":
                System.err.printf("%f%n", stack.popDouble());
                break;
            case "(Ljava/lang/String;)V":
                JvmObject jStr = stack.popRef();
                System.err.println(StringPool.getString(jStr));
            default:
                throw new RuntimeException("println: " + descriptor);
        }
        stack.popRef();
    }
}
