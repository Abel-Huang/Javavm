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
public class BootstrapTest {

    @Test
    public void startTest() throws IOException {
        String[] args = {"-Xjre", System.getProperty("java.home"), "java.lang.String"};
        Bootstrap.start(args);
    }

    @Test
    public void startJVMTest() throws IOException {
        Command command = new Command();
        // 使用当前 JVM 的 home 目录作为 JRE 路径
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        command.setXJreOption(javaHome);
        // 设置类路径指向测试类目录（绝对路径）
        command.setCpOption(projectPath + "/target/test-classes");
        // 使用项目中的测试类
        command.setClazz("cn.abelib.javavm.testcase.Hello");
        Bootstrap.startJVM(command);
    }
}
