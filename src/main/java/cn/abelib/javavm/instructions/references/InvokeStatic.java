package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.instructions.base.InitClazz;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.Method;
import cn.abelib.javavm.runtime.heap.MethodRef;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/6/1 0:51
 */
public class InvokeStatic extends Index16Instruction implements MethodInvokeInstruction {

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
        if (!resolvedMethod.isStatic()) {
            throw new RuntimeException("java.lang.IncompatibleClassChangeError");
        }

        Clazz clazz = resolvedMethod.getClazz();
        if (!clazz.isInitStarted()) {
            frame.revertPc();
            InitClazz.initClass(frame.getThread(), clazz);
            return;
        }

        invokeMethod(frame, resolvedMethod);
    }
}
