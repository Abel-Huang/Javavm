package cn.abelib.javavm.clazz;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/29 0:13
 */
public class ClazzTest {

    @Test
    public void startTest() throws IOException {
        String[] args = {"-Xjre", "D:\\dev\\jdk\\jre", "-cp", "java.lang.String"};
        Bootstrap.start(args);
    }

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\Javavm\\datas");
        command.setClazz("java.lang.String");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
