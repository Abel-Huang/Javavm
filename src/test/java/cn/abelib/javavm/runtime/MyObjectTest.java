package cn.abelib.javavm.runtime;

import cn.abelib.javavm.Bootstrap;
import cn.abelib.javavm.Command;
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
        // 使用系统环境变量或相对路径
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        command.setXJreOption(javaHome);
        command.setCpOption(projectPath + "/target/test-classes");
        command.setClazz("cn.abelib.javavm.testcase.MyObject");
        Bootstrap.startJVM(command);
    }
}
