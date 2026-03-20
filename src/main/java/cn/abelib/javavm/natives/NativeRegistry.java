package cn.abelib.javavm.natives;

import cn.abelib.javavm.runtime.Frame;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/19 23:11
 */
public class NativeRegistry {

    public static Map<String, NativeMethod> registry = Maps.newHashMap();

    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        if (registry.containsKey(key)) {
            return registry.get(key);
        }
        if (methodDescriptor.equals("()V") && methodName.equals("registerNatives")) {
            return emptyNativeMethod(null);
        }
        return null;
    }

    public static NativeMethod emptyNativeMethod(Frame frame) {
        // do nothing
        return frameItem -> {

        };
    }
}
