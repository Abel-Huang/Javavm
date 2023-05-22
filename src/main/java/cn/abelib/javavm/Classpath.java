package cn.abelib.javavm;

import cn.abelib.javavm.entry.WildcardEntry;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/12 17:17
 */
public class Classpath {
    private Entry bootClasspath;
    private Entry extClasspath;
    private Entry userClasspath;

    public Classpath() {

    }

    public void parse(String jreOption, String cpOption) {
        parseBootAndExtClasspath(jreOption);
        parseUserClasspath(cpOption);
    }

    private void parseBootAndExtClasspath(String jreOption) {
        String jreDir = getJreDir(jreOption);
        // jre/lib/*
        String jreLibPath = Joiner.on("\\").join(jreDir, "lib", "*");
        this.bootClasspath = new WildcardEntry(jreLibPath);
        // jre/lib/ext/*
        String jreExtPath = Joiner.on("\\").join(jreDir, "lib", "ext", "*");
        this.extClasspath = new WildcardEntry(jreExtPath);
    }

    private String getJreDir(String jreOption)  {
        if(StringUtils.isNotBlank(jreOption) && exists(jreOption)) {
            return jreOption;
        }
        if (exists("./jre")) {
            return "./jre";
        }
        String javaHome = System.getenv("JAVA_HOME");
        if (StringUtils.isNotBlank(javaHome)) {
            return Joiner.on("\\").join(javaHome, "jre");
        }
        throw new RuntimeException("Can not find jre folder");
    }

    private boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    private void parseUserClasspath(String cpOption) {
        if (StringUtils.isBlank(cpOption)) {
            cpOption = "";
        }
        this.userClasspath = Entry.newEntry(cpOption);
    }

    public byte[] readClass(String className) throws IOException {
        className = className.replace(".", "/");
        className = className + ".class";
        byte[] bytes = this.bootClasspath.readClass(className);
        if (ArrayUtils.isNotEmpty(bytes)) {
            return bytes;
        }
        bytes = this.extClasspath.readClass(className);
        if (ArrayUtils.isNotEmpty(bytes)) {
            return bytes;
        }
        return this.userClasspath.readClass(className);
    }

    public String string() {
        return this.userClasspath.pathString();
    }
}
