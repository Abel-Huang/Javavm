package cn.abelib.javavm.natives;

import cn.abelib.javavm.runtime.Frame;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/19 23:16
 */
public interface NativeMethod {

    void execute(Frame frame);
}
