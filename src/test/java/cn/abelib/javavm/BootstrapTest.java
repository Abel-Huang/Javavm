package cn.abelib.javavm;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/29 0:13
 */
public class BootstrapTest {

    @Test
    @Ignore("测试不通过")
    public void startTest() throws IOException {
        String[] args = {"-Xjre", "D:\\dev\\jdk\\jre", "java.lang.String"};
        Bootstrap.start(args);
    }

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("");
        command.setClazz("java.lang.Integer");
        Bootstrap.startJVM(command);
    }
}
