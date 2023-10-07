package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.LocalVars;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/7 23:25
 */
public class SystemArraycopy implements NativeMethod {

    /**
     * public final native Class<?> getClass();
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        LocalVars vars = frame.getLocalVars();
        JvmObject src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        JvmObject dest = vars.getRef(2);
        int destPos = vars.getInt(3);
        int length = vars.getInt(4);

        if (src == null || dest == null) {
            throw new RuntimeException("java.lang.NullPointerException");
        }

        if (!checkArrayCopy(src, dest)) {
            throw new RuntimeException("java.lang.ArrayStoreException");
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos+length > src.getArrayLength() ||
                destPos+length > dest.getArrayLength()) {
            throw new RuntimeException("java.lang.IndexOutOfBoundsException");
        }

        System.arraycopy(src, srcPos, dest, destPos, length);
    }

    private boolean checkArrayCopy(JvmObject src, JvmObject dest) {
        Clazz srcClazz = src.getClazz();
        Clazz destClazz = dest.getClazz();
        if (!srcClazz.isArray() || !destClazz.isArray()) {
            return false;
        }
        try {
            if (srcClazz.getComponentClass().isPrimitive() ||
                    destClazz.getComponentClass().isPrimitive()) {
                return srcClazz == destClazz;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
