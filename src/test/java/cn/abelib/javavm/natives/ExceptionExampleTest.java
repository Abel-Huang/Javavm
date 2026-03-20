package cn.abelib.javavm.natives;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
import org.junit.Test;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/21 23:16
 */
public class ExceptionExampleTest {
    /**
     * @throws IOException
     */
    @Test
    public void exceptionTest() throws IOException {
        Command command = new Command();
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        command.setXJreOption(javaHome);
        command.setCpOption(projectPath + "/target/test-classes");
        command.setClazz("cn.abelib.javavm.testcase.ExceptionExample");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
