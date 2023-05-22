package cn.abelib.javavm;

import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 21:47
 */
public class InstructionTest {
    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\fresh-melon\\datas");
        command.setClazz("GaussTest");
        Bootstrap.startJVM(command);
    }
}
