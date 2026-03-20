package cn.abelib.javavm.instructions.constants;

import cn.abelib.javavm.instructions.base.Index16Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.*;
import cn.abelib.javavm.runtime.heap.ClassLoader;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/11 21:25
 */
public class LoadConstantWide extends Index16Instruction {
    @Override
    public void execute(Frame frame) {
        OperandStack stack = frame.getOperandStack();
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        RuntimeConstantPoolInfo poolInfo = cp.getConstant(index);
        
        // ldc_w 可以处理 int, float, String, Class 类型（使用 2 字节索引）
        if (poolInfo.isSetIntValue()) {
            stack.pushInt(poolInfo.getIntValue());
            return;
        } else if (poolInfo.isSetFloatValue()) {
            stack.pushFloat(poolInfo.getFloatValue());
            return;
        } else if (poolInfo.isSetStringValue()) {
            // support string
            Clazz clazz = frame.getMethod().getClazz();
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
        throw new RuntimeException("ldc_w: unsupported constant type");
    }
}
