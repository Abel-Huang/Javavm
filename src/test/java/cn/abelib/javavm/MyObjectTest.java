package cn.abelib.javavm;

import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 23:30
 */
public class MyObjectTest {

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\Javavm\\datas");
        command.setClazz("MyObject");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
