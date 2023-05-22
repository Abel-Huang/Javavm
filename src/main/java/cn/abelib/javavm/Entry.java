package cn.abelib.javavm;

import cn.abelib.javavm.entry.CompositeEntry;
import cn.abelib.javavm.entry.DirEntry;
import cn.abelib.javavm.entry.WildcardEntry;
import cn.abelib.javavm.entry.ZipEntry;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 1:35
 */
public interface Entry {
    // 路径分隔符
    String pathListSeparator = ",";

    /**
     * 读取的是相对路径
     * @param className
     * @return
     * @throws IOException
     */
    byte[] readClass(String className) throws IOException;

    String pathString();

    static Entry newEntry(String path) {
        if (StringUtils.contains(path, pathListSeparator)) {
            return new CompositeEntry(path);
        }
        if (StringUtils.endsWith(path, "*")) {
            WildcardEntry wildcardEntry = new WildcardEntry(path);
            return wildcardEntry;
        }
        if (StringUtils.endsWith(path, "jar") || StringUtils.endsWith(path, "JAR")
        || StringUtils.endsWith(path, "zip") || StringUtils.endsWith(path, "ZIP")) {
            return new ZipEntry(path);
        }
        return new DirEntry(path);
    }
}
