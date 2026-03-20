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

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        command.setXJreOption(javaHome);
        command.setCpOption(projectPath + "/target/test-classes");
        command.setClazz("cn.abelib.javavm.testcase.GaussTest");
        Bootstrap.startJVM(command);
    }
}
