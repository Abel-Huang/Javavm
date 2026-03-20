package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.instructions.base.InitClazz;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.ClassRef;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/5 22:52
 */
public class New extends Index16Instruction {

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = cp.getConstant(this.index).getClassRef();
        Clazz clazz = null;
        try {
            clazz = classRef.resolvedClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang.InstantiationError");
        }
        if (!clazz.isInitStarted()) {
            frame.revertPc();
            InitClazz.initClass(frame.getThread(), clazz);
            return;
        }

        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new RuntimeException("java.lang.InstantiationError");
        }
        JvmObject ref = clazz.newObject();
        frame.getOperandStack().pushRef(ref);
    }
}
