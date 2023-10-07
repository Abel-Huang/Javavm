package cn.abelib.javavm.runtime;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 21:47
 */
public class InstructionTest {

    /**
     * test for gauss alg, add 1 to 100
     * @throws IOException
     */
    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        command.setXJreOption("D:\\dev\\jdk\\jre");
        command.setCpOption("D:\\project\\java\\Javavm\\datas");
        command.setClazz("GaussTest");
        command.setVerboseInstFlag(false);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
