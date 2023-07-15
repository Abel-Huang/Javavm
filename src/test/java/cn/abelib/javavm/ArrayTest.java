package cn.abelib.javavm;

import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/13 22:56
 */
public class ArrayTest {

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\Javavm\\datas");
        command.setClazz("BubbleSortTest");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
