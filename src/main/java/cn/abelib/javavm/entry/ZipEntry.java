package cn.abelib.javavm.entry;

import cn.abelib.javavm.Entry;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipFile;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 1:53
 */
public class ZipEntry implements Entry {
    private Path absDir;

    public ZipEntry(String path) {
        // 转为绝对路径，
        // 如果当前路径不是绝对路径则将当前工作路径作为绝对路径
        absDir = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        // 读取zip文件中文件，找到和和className文件名相同的文件
        try(ZipFile zipFile = new ZipFile(absDir.toFile())) {
            Enumeration<? extends java.util.zip.ZipEntry> entries =  zipFile.entries();
            while (entries.hasMoreElements()) {
                java.util.zip.ZipEntry zipEntry = entries.nextElement();
                if (zipEntry.getName().equals(className)) {
                    return IOUtils.toByteArray(zipFile.getInputStream(zipEntry));
                }
            }
        }

        return new byte[0];
    }

    @Override
    public String pathString() {
        return absDir.toString();
    }
}
