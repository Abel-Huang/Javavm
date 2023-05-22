package cn.abelib.javavm.entry;

import cn.abelib.javavm.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 1:54
 */
public class DirEntry implements Entry {
    private Path absDir;

    public DirEntry(String path) {
        // 转为绝对路径，
        // 如果当前路径不是绝对路径则将当前工作路径作为绝对路径
        absDir = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        Path path = Paths.get(pathString(), className);
        if (Files.exists(path)) {
            return Files.readAllBytes(path);
        }
        return new byte[0];
    }

    @Override
    public String pathString() {
        return this.absDir.toString();
    }
}
