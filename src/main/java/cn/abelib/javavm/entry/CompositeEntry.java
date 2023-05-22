package cn.abelib.javavm.entry;

import cn.abelib.javavm.Entry;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 1:49
 */
public class CompositeEntry implements Entry {
    private List<Entry> entries;

    public CompositeEntry(String path) {
        String[] pathsList = path.split(pathListSeparator);
        entries = Arrays.stream(pathsList)
                .map(Entry::newEntry)
                .collect(Collectors.toList());
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for(Entry entry : entries) {
            byte[] bytes = entry.readClass(className);
            if (ArrayUtils.isNotEmpty(bytes)) {
                return bytes;
            }
        }
        throw new RuntimeException("class not found: " + className);
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
