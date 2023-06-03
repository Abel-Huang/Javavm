package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.Method;
import cn.abelib.javavm.runtime.heap.MethodRef;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/1 1:18
 */
public class InvokeInterface implements Instruction, MethodInvokeInstruction {
    private int index;
    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUInt16();
        reader.readUInt8(); // count
        reader.readUInt8(); // must be 0
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = cp.getConstant(this.index).getMethodRef();

        Method resolvedMethod;
        try {
            resolvedMethod = methodRef.resolvedMethod();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }

        JvmObject ref = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (ref == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }
        try {
            if (!ref.getClazz().isImplements(methodRef.resolvedClass())) {
                throw new RuntimeException("java.lang.IncompatibleClassChangeError");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }

        Method methodToBeInvoked = methodRef.lookupMethodInClass(ref.getClazz(),
                methodRef.getMethodName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new RuntimeException("java.lang.AbstractMethodError");
        }
        if (!methodToBeInvoked.isPublic()) {
            throw new RuntimeException("java.lang.IllegalAccessError");
        }

        invokeMethod(frame, methodToBeInvoked);
    }
}
