package cn.abelib.javavm.instructions.reserved;

import cn.abelib.javavm.instructions.base.NoOperandsInstruction;
import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.natives.NativeRegistry;
import cn.abelib.javavm.natives.java.io.FileOutputStreamIntern;
import cn.abelib.javavm.natives.java.lang.*;
import cn.abelib.javavm.natives.sun.misc.VMInitialize;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.heap.Method;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/20 23:19
 */
public class InvokeNative extends NoOperandsInstruction {
    static {
        NativeRegistry.register("java/lang/Float",
                "floatToRawIntBits",
                "(F)I",
                new FloatToRawIntBits());

        NativeRegistry.register("sum/misc/VM",
                "initialize",
                "()V",
                new VMInitialize());

        NativeRegistry.register("java/lang/Throwable",
                "fillInStackTrace",
                "(I)Ljava/lang/Throwable;",
                new ThrowableFillInStackTrace());

        NativeRegistry.register("java/lang/System",
                "arraycopy",
                "(Ljava/lang/Object;ILjava/lang/Object;II)V",
                new SystemArraycopy());

        final String FOS = "java/io/FileOutputStream";
        NativeRegistry.register(FOS,
                "writeBytes",
                "([BIIZ)V",
                new StringWriteBytes());

        NativeRegistry.register("java/lang/Object",
                "getClass",
                "()Ljava/lang/Class;",
                new ObjectGetClass());

        NativeRegistry.register("java/lang/Object",
                "hashCode",
                "()I",
                new ObjectHashCode());

        NativeRegistry.register("java/lang/Object",
                "clone",
                "()Ljava/lang/Object;",
                new ObjectClone());

        NativeRegistry.register("java/lang/Class",
                "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;",
                new ClazzGetPrimitiveClass());

        NativeRegistry.register("java/lang/io",
                "intern",
                "()Ljava/lang/String;",
                new FileOutputStreamIntern());

        NativeRegistry.register("java/lang/Class",
                "getName0",
                "()Ljava/lang/String;",
                new ClazzGetName0());

        NativeRegistry.register("java/lang/Class",
                "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z",
                new ClazzDesiredAssertionStatus0());

        NativeRegistry.register("java/lang/Class",
                "setOut0",
                "(Ljava/lang/Class;)Z",
                new ClazzSetOut0());
    }

    @Override
    public void execute(Frame frame) {
        Method method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        NativeMethod nativeMethod = NativeRegistry.findNativeMethod(className,
                methodName,
                methodDescriptor);
        if (nativeMethod == null) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new RuntimeException("java.lang.UnsatisfiedLinkError: " + methodInfo);
        }


        nativeMethod.execute(frame);
    }
}
