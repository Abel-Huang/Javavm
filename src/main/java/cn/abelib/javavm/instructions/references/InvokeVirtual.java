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
        RuntimeConstantPool cp = currentClass.getConstantPool();
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
        
        int argSlotCount = resolvedMethod.getArgSlotCount();
        OperandStack stack = frame.getOperandStack();
        
        // 确保栈上有足够的参数
        if (argSlotCount < 1 || stack.getSize() < argSlotCount) {
            throw new RuntimeException("Stack too small for method invocation: " + methodRef.getMethodName());
        }
        
        // 获取对象引用
        JvmObject ref;
        if (argSlotCount == 1) {
            // 对于没有额外参数的方法（只有 this），直接 pop
            ref = stack.popRef();
            // 重新压入栈（因为后续逻辑需要它）
            stack.pushRef(ref);
        } else {
            ref = stack.getRefFromTop(argSlotCount - 1);
        }
        
        // todo hack! delete println hack
        if (ref == null) {
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

        // 在对象的实际类中查找方法（而不是调用者的父类）
        Method methodToBeInvoked = methodRef.lookupMethodInClass(ref.getClazz(),
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
                break;
            default:
                throw new RuntimeException("println: " + descriptor);
        }
        stack.popRef();
    }
}
