package cn.abelib.javavm.runtime;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/30 16:48
 */
public class NewObjectTest {

    /**
     * fixme
     * java.lang.NullPointerException
     * 	at cn.abelib.javavm.runtime.LocalVars.getRef(LocalVars.java:87)
     * 	at cn.abelib.javavm.runtime.LocalVars.getThis(LocalVars.java:91)
     * 	at cn.abelib.javavm.natives.java.lang.NativeClazz.getPrimitiveClass(NativeClazz.java:40)
     * 	at cn.abelib.javavm.natives.java.lang.NativeClazz.init(NativeClazz.java:24)
     * 	at cn.abelib.javavm.instructions.reserved.InvokeNative.execute(InvokeNative.java:33)
     * 	at cn.abelib.javavm.Interpreter.loop(Interpreter.java:55)
     * 	at cn.abelib.javavm.Interpreter.interpret(Interpreter.java:33)
     * 	at cn.abelib.javavm.Bootstrap.startJVM(Bootstrap.java:41)
     * 	at cn.abelib.javavm.runtime.NewObjectTest.startJVMTest(NewObjectTest.java:22)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
     * 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
     * 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
     * 	at java.lang.reflect.Method.invoke(Method.java:498)
     */
    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\Javavm\\datas");
        command.setClazz("NewObject");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
