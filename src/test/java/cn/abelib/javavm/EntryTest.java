package cn.abelib.javavm;

import cn.abelib.javavm.Entry;
import cn.abelib.javavm.entry.DirEntry;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 2:24
 */
public class EntryTest {

    @Test
    public void dirEntryTest() throws IOException {
        Entry entry = new DirEntry("datas");
        System.err.println(entry.pathString());
        entry.readClass("Hello.java");
    }

    @Test
    public void zipTest() throws IOException {
        ZipFile zipFile = new ZipFile("D:\\project\\java\\fresh-melon\\datas\\test.zip");
        zipFile.stream().forEach(zipEntry -> {
            System.out.println(zipEntry.getName());
        });
    }

    @Test
    public void zipTest2() throws IOException {
        Path absDir = Paths.get("D:\\dev\\jdk\\jre\\lib\\rt.jar");
        try(ZipFile zipFile = new ZipFile(absDir.toFile())) {
            Enumeration<? extends ZipEntry> entries =  zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();
                System.out.println(zipEntry.getName());
            }
        }
    }

    @Test
    public void walkTest() throws IOException {
        Path absDir = Paths.get("D:\\project\\java\\fresh-melon\\datas");
        Files.walk(absDir, 1, FileVisitOption.FOLLOW_LINKS)
                .forEach(subPath -> System.out.println(subPath.toString()));
    }
}
