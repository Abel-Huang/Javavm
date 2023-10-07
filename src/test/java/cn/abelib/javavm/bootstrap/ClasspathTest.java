package cn.abelib.javavm.bootstrap;

import cn.abelib.javavm.Classpath;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 18:26
 */
public class ClasspathTest {

    @Test
    public void classpathTest() throws IOException {
        Classpath classpath = new Classpath();
        classpath.parse("D:\\dev\\jdk\\jre", "");
        byte[] bytes = classpath.readClass("java.lang.Object");
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void toAbsolutePathTest() {
        String path = "dev\\jdk\\jre";
        Path absDir = Paths.get(path).toAbsolutePath();
        System.out.println(absDir);
    }

    @Test
    public void splitTest() {
        String path = "D:\\dev\\jdk\\jre\\lib\\, D:\\dev\\jdk\\jre";
        String[] pathsList = path.split(",");
        System.out.println(Arrays.toString(pathsList));
    }
}
