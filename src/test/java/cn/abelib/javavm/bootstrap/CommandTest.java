package cn.abelib.javavm.bootstrap;

import cn.abelib.javavm.Command;
import org.junit.Test;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/9/8 0:08
 */
public class CommandTest {

    @Test
    public void parseCmdTest1() {
        Command command = new Command();
        String[] args = {"-h"};
        command.parseCmd(args);
    }

    @Test
    public void parseCmdTest2() {
        Command command = new Command();
        String[] args = {"-Xjre", "D:\\dev\\jdk\\jre", "-cp", "java.lang.String"};
        command.parseCmd(args);
        System.out.println(command.getClazz());
        System.out.println(command.getXJreOption());
    }
}
