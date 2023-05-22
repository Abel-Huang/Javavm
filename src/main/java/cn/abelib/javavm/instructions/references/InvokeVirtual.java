package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.ClassRef;
import cn.abelib.javavm.runtime.heap.MethodRef;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 22:29
 */
public class InvokeVirtual extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        // todo
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = cp.getConstant(this.index).getMethodRef();

        if ("println".equals(methodRef.getMethodName())) {
            OperandStack stack = frame.getOperandStack();
            switch (methodRef.getDescriptor()) {
                case "(Z)V":
                    System.err.println(String.format("%v\n", stack.popInt() != 0));
                    break;
                case "(C)V":
                case "(B)V":
                case "(S)V":
                case "(I)V":
                    System.err.println(String.format("%v\n", stack.popInt()));
                    break;
                case "(J)V":
                    System.err.println(String.format("%v\n", stack.popLong()));
                    break;
                case "(F)V":
                    System.err.println(String.format("%v\n", stack.popFloat()));
                    break;
                case "(D)V":
                    System.err.println(String.format("%v\n", stack.popDouble()));
                    break;
                default:
                    throw new RuntimeException("println: " + methodRef.getDescriptor());
            }
            stack.popRef();
        }
    }
}
