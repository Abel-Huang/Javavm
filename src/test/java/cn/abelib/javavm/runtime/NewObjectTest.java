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

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        command.setXJreOption(javaHome);
        command.setCpOption(projectPath + "/target/test-classes");
        command.setClazz("cn.abelib.javavm.testcase.NewObject");
        command.setVerboseInstFlag(true);
        command.setVerboseClassFlag(true);
        Bootstrap.startJVM(command);
    }
}
