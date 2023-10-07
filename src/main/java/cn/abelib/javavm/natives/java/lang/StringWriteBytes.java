package cn.abelib.javavm.natives.java.lang;

import cn.abelib.javavm.natives.NativeMethod;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.LocalVars;
import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/7 23:59
 */
public class StringWriteBytes implements NativeMethod {

    /**
     * private native void writeBytes(byte b[], int off, int len, boolean append)
     * @param frame
     */
    @Override
    public void execute(Frame frame) {
        LocalVars vars = frame.getLocalVars();
        JvmObject b = vars.getRef(1);
        int off = vars.getInt(2);
        int len = vars.getInt(3);

        List<Integer> ints = b.getBytes();
        // todo convert bytes
        // output bytes
        System.err.println(new String(getBytes(ints, off, len)));
    }

    /**
     * List<Integer> -> byte[]
     * @param ints
     * @param off
     * @param len
     * @return
     */
    private byte[] getBytes(List<Integer> ints, int off, int len) {
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i ++) {
            bytes[i] = ints.get(i + off).byteValue();
        }
        return bytes;
    }
}
