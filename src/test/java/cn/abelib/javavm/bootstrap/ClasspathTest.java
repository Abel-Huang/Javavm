package cn.abelib.javavm.bootstrap;

import cn.abelib.javavm.Classpath;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 18:26
 */
public class ClasspathTest {

    @Test
    public void testClasspathParse() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        assertNotNull("Classpath 不应为 null", classpath);
    }

    @Test
    public void testReadJavaLangObject() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        byte[] bytes = classpath.readClass("java.lang.Object");
        assertNotNull("应该能读取 java.lang.Object", bytes);
        assertTrue("类文件应该有内容", bytes.length > 0);
        
        // 验证魔数 (0xCAFEBABE)
        assertEquals("魔数第一个字节", 0xCA, bytes[0] & 0xFF);
        assertEquals("魔数第二个字节", 0xFE, bytes[1] & 0xFF);
        assertEquals("魔数第三个字节", 0xBA, bytes[2] & 0xFF);
        assertEquals("魔数第四个字节", 0xBE, bytes[3] & 0xFF);
    }

    @Test
    public void testReadJavaLangString() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        byte[] bytes = classpath.readClass("java.lang.String");
        assertNotNull("应该能读取 java.lang.String", bytes);
        assertTrue("类文件应该有内容", bytes.length > 0);
    }

    @Test
    public void testReadClassWithSlashSeparator() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        // 使用 / 分隔符
        byte[] bytes = classpath.readClass("java/lang/Object");
        assertNotNull("应该能读取 java/lang/Object", bytes);
        assertTrue("类文件应该有内容", bytes.length > 0);
    }

    @Test
    public void testToAbsolutePath() {
        String path = "dev/jdk/jre";
        Path absDir = Paths.get(path).toAbsolutePath();
        assertNotNull("绝对路径不应为 null", absDir);
        assertTrue("绝对路径应该包含原始路径", absDir.toString().contains("dev"));
    }

    @Test
    public void testSplitPath() {
        String path = "/dev/jdk/jre/lib/, /dev/jdk/jre";
        String[] pathsList = path.split(",");
        assertEquals("应该分割为 2 个路径", 2, pathsList.length);
        assertEquals("第一个路径", "/dev/jdk/jre/lib/", pathsList[0].trim());
        assertEquals("第二个路径", "/dev/jdk/jre", pathsList[1].trim());
    }

    @Test
    public void testClasspathString() {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        classpath.parse(javaHome, projectPath + "/target/test-classes");
        
        String cpString = classpath.string();
        assertNotNull("类路径字符串不应为 null", cpString);
        assertTrue("类路径应包含项目路径", cpString.contains("target/test-classes"));
    }
}
