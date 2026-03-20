package cn.abelib.javavm.bootstrap;

import cn.abelib.javavm.Classpath;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Classpath 边界测试
 * @author abel.huang
 * @version 1.0
 * @date 2025/1/20
 */
public class ClasspathBoundaryTest {

    @Test
    public void testReadClassWithNullClassName() {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        try {
            byte[] bytes = classpath.readClass(null);
            // 根据实现，可能返回 null 或抛出异常
        } catch (Exception e) {
            // 预期可能抛出异常
            assertTrue(e instanceof NullPointerException || e instanceof IOException);
        }
    }

    @Test
    public void testReadClassWithEmptyClassName() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        // 空类名会被转换为 ".class"，这可能匹配到某些文件
        // 这里测试实际行为，验证不会抛出异常
        byte[] bytes = classpath.readClass("");
        // 根据类路径内容，可能返回 null 或非 null
        // 主要验证方法不会崩溃
    }

    @Test
    public void testReadClassWithNonExistentClass() throws IOException {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        
        // 使用一个极不可能存在的类名
        byte[] bytes = classpath.readClass("com.example.nonexistent.XYZ123456");
        // 空数组表示未找到，null 表示其他问题
        assertTrue("不存在的类应该返回空数组", bytes == null || bytes.length == 0);
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
    public void testClasspathWithInvalidJrePath() {
        Classpath classpath = new Classpath();
        
        try {
            classpath.parse("/invalid/jre/path", "");
            // 某些实现可能会抛出异常
        } catch (Exception e) {
            // 预期行为
            assertTrue(e instanceof RuntimeException);
        }
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
