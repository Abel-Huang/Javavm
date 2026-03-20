package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;
import cn.abelib.javavm.runtime.heap.ClassLoader;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:32
 */
public interface LoadConstantInstruction {
    default void ldc(Frame frame, int index) {
        OperandStack stack = frame.getOperandStack();
        Clazz clazz = frame.getMethod().getClazz();
        RuntimeConstantPool cp = clazz.getConstantPool();
        RuntimeConstantPoolInfo poolInfo = cp.getConstant(index);
        if (poolInfo.isSetIntValue()) {
            stack.pushInt(poolInfo.getIntValue());
            return;
        } else if (poolInfo.isSetFloatValue()) {
            stack.pushFloat(poolInfo.getFloatValue());
            return;
        } else if (poolInfo.isSetStringValue()) {
            // support string
            JvmObject strRef = StringPool.getString(clazz.getClassLoader(), poolInfo.getStringValue());
            stack.pushRef(strRef);
            return;
        } else if (poolInfo.isSetClassRef()) {
            ClassRef classRef = poolInfo.getClassRef();
            JvmObject classObj = null;
            Clazz resolvedClazz = null;
            try {
                resolvedClazz = classRef.resolvedClass();
                if (resolvedClazz == null) {
                    throw new RuntimeException("resolvedClass returned null for classRef");
                }
                classObj = resolvedClazz.getJClass();
                if (classObj == null) {
                    // 尝试重新创建 JClass
                    ClassLoader classLoader = resolvedClazz.getClassLoader();
                    if (classLoader != null) {
                        Clazz jlClassClass = classLoader.loadClass("java/lang/Class");
                        classObj = jlClassClass.newObject();
                        classObj.setExtra(resolvedClazz);
                        resolvedClazz.setJClass(classObj);
                    }
                }
                if (classObj == null) {
                    throw new RuntimeException("Failed to create JClass object for: " + resolvedClazz.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("IOException", e);
            }
            if (classObj == null) {
                throw new RuntimeException("Failed to create JClass object");
            }
            stack.pushRef(classObj);
            return;
        }
        throw new RuntimeException("todo: ldc!");
    }
}
