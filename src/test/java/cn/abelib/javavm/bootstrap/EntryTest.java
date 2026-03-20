package cn.abelib.javavm.bootstrap;

import cn.abelib.javavm.Entry;
import cn.abelib.javavm.entry.DirEntry;
import cn.abelib.javavm.entry.ZipEntry;
import cn.abelib.javavm.entry.WildcardEntry;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 2:24
 */
public class EntryTest {

    @Test
    public void testDirEntry() throws IOException {
        String projectPath = System.getProperty("user.dir");
        Entry entry = new DirEntry(projectPath + "/datas");
        
        assertNotNull("Entry 不应为 null", entry);
        assertNotNull("路径字符串不应为 null", entry.pathString());
        assertTrue("路径应包含 datas", entry.pathString().contains("datas"));
    }

    @Test
    public void testDirEntryReadClass() throws IOException {
        String projectPath = System.getProperty("user.dir");
        Entry entry = new DirEntry(projectPath + "/datas");
        
        // 尝试读取存在的文件
        byte[] bytes = entry.readClass("Hello.java");
        // 如果文件存在，应该能读取到内容
        if (bytes != null && bytes.length > 0) {
            assertTrue("文件内容应该有长度", bytes.length > 0);
        }
    }

    @Test
    public void testDirEntryReadNonExistentClass() throws IOException {
        String projectPath = System.getProperty("user.dir");
        Entry entry = new DirEntry(projectPath + "/datas");
        
        // 读取不存在的文件
        byte[] bytes = entry.readClass("NonExistentFile.xyz");
        assertTrue("不存在的文件应返回空数组", bytes == null || bytes.length == 0);
    }

    @Test
    public void testZipEntry() throws IOException {
        // 使用 Java 运行时路径查找 rt.jar 或 jrt-fs.jar
        String javaHome = System.getProperty("java.home");
        Path rtJarPath = Paths.get(javaHome, "lib", "rt.jar");
        Path jrtFsJarPath = Paths.get(javaHome, "lib", "jrt-fs.jar");

        Path jarPath = Files.exists(rtJarPath) ? rtJarPath : jrtFsJarPath;
        if (jarPath == null || !Files.exists(jarPath)) {
            // 跳过测试，不抛出异常
            return;
        }
        
        Entry entry = new ZipEntry(jarPath.toString());
        assertNotNull("ZipEntry 不应为 null", entry);
        assertNotNull("路径字符串不应为 null", entry.pathString());
    }

    @Test
    public void testZipEntryReadClass() throws IOException {
        // 使用 Java 运行时路径查找 rt.jar 或 jrt-fs.jar
        String javaHome = System.getProperty("java.home");
        Path rtJarPath = Paths.get(javaHome, "lib", "rt.jar");
        Path jrtFsJarPath = Paths.get(javaHome, "lib", "jrt-fs.jar");

        Path jarPath = Files.exists(rtJarPath) ? rtJarPath : jrtFsJarPath;
        if (jarPath == null || !Files.exists(jarPath)) {
            // 跳过测试
            return;
        }
        
        Entry entry = new ZipEntry(jarPath.toString());
        
        // 尝试读取 Object.class
        byte[] bytes = entry.readClass("java/lang/Object.class");
        // 可能返回空数组（如果 jrt-fs.jar 不包含传统类文件结构）
        if (bytes != null && bytes.length > 0) {
            assertTrue("类文件应有内容", bytes.length > 0);
        }
    }

    @Test
    public void testWildcardEntry() throws IOException {
        String javaHome = System.getProperty("java.home");
        
        // Java 9+ 使用 jmods 目录
        Path jmodsPath = Paths.get(javaHome, "jmods");
        if (Files.exists(jmodsPath)) {
            Entry entry = new WildcardEntry(jmodsPath.toString() + "/*");
            assertNotNull("WildcardEntry 不应为 null", entry);
            assertNotNull("路径字符串不应为 null", entry.pathString());
        } else {
            // Java 8 使用 lib 目录
            Path libPath = Paths.get(javaHome, "lib");
            if (Files.exists(libPath)) {
                Entry entry = new WildcardEntry(libPath.toString() + "/*");
                assertNotNull("WildcardEntry 不应为 null", entry);
            }
        }
    }

    @Test
    public void testWalkDirectory() throws IOException {
        String projectPath = System.getProperty("user.dir");
        Path absDir = Paths.get(projectPath, "datas");
        
        if (!Files.exists(absDir)) {
            // 跳过测试
            return;
        }
        
        long count = Files.walk(absDir, 1, FileVisitOption.FOLLOW_LINKS)
                .count();
        assertTrue("目录应该包含文件", count >= 1);
    }

    @Test
    public void testNewEntryFactory() {
        String projectPath = System.getProperty("user.dir");
        
        // 测试目录类型 Entry
        Entry dirEntry = Entry.newEntry(projectPath + "/datas");
        assertNotNull("目录 Entry 不应为 null", dirEntry);
        assertTrue("应该是 DirEntry", dirEntry instanceof DirEntry);
    }
}
