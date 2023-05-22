package cn.abelib.javavm.entry;

import cn.abelib.javavm.Entry;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 1:52
 */
public class WildcardEntry implements Entry {
    private Path absDir;
    private List<Entry> entries;

    public WildcardEntry(String path) {
        path = StringUtils.removeEnd(path, "*");
        // 转为绝对路径
        absDir = Paths.get(path).toAbsolutePath();
        try {
            entries = Files.walk(absDir, 1, FileVisitOption.FOLLOW_LINKS)
                    .map(subPath -> {
                        String jarPath = subPath.toString();
                        if (StringUtils.endsWith(jarPath, ".jar")
                                || StringUtils.endsWith(jarPath, ".JAR")) {
                            return Entry.newEntry(jarPath);
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
        }
        entries = CollectionUtils.isEmpty(entries)
                ? Lists.newArrayList()
                : entries;
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for(Entry entry : entries) {
            byte[] bytes = entry.readClass(className);
            if (ArrayUtils.isNotEmpty(bytes)) {
                return bytes;
            }
        }
        return new byte[0];
    }

    @Override
    public String pathString() {
        StringBuilder pathBuilder = new StringBuilder();
        for (int i = 0; i < entries.size(); i ++) {
            if (i != 0) {
                pathBuilder.append(pathListSeparator);
            }
            pathBuilder.append(entries.get(i).pathString());
        }
        return pathBuilder.toString();
    }
}
