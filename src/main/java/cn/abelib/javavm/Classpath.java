package cn.abelib.javavm;

import cn.abelib.javavm.entry.WildcardEntry;
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
        // Java 9+ 使用 jmods 目录，Java 8 使用 lib 目录
        String jmodsPath = jreDir + java.io.File.separator + "jmods" + java.io.File.separator + "*";
        if (exists(jreDir + java.io.File.separator + "jmods")) {
            this.bootClasspath = new WildcardEntry(jmodsPath);
        } else {
            // Java 8: jre/lib/*
            String jreLibPath = jreDir + java.io.File.separator + "lib" + java.io.File.separator + "*";
            this.bootClasspath = new WildcardEntry(jreLibPath);
        }
        // jre/lib/ext/* (Java 8)
        String jreExtPath = jreDir + java.io.File.separator + "lib" + java.io.File.separator + "ext" + java.io.File.separator + "*";
        this.extClasspath = new WildcardEntry(jreExtPath);
    }

    private String getJreDir(String jreOption)  {
        if(StringUtils.isNotBlank(jreOption) && exists(jreOption)) {
            return jreOption;
        }
        if (exists("./jre")) {
            return "./jre";
        }
        // 尝试查找当前目录下的 JDK
        if (exists(".")) {
            String[] possibleDirs = {".", "jdk", "java"};
            for (String dir : possibleDirs) {
                if (exists(dir)) {
                    // Java 9+: jmods 目录
                    if (exists(dir + java.io.File.separator + "jmods")) {
                        return dir;
                    }
                    // Java 8: jre 目录或 lib 目录
                    if (exists(dir + java.io.File.separator + "jre")) {
                        return dir + java.io.File.separator + "jre";
                    }
                }
            }
        }
        // 查找 JAVA_HOME
        String javaHome = System.getenv("JAVA_HOME");
        if (StringUtils.isNotBlank(javaHome)) {
            if (exists(javaHome)) {
                // Java 9+: jmods 目录
                if (exists(javaHome + java.io.File.separator + "jmods")) {
                    return javaHome;
                }
                // Java 8: jre 目录
                String jrePath = javaHome + java.io.File.separator + "jre";
                if (exists(jrePath)) {
                    return jrePath;
                }
                // 直接使用 JAVA_HOME
                return javaHome;
            }
        }
        // 最后尝试使用 java.home 系统属性（当前运行 JVM 的路径）
        String javaHomeProp = System.getProperty("java.home");
        if (StringUtils.isNotBlank(javaHomeProp) && exists(javaHomeProp)) {
            return javaHomeProp;
        }
        throw new RuntimeException("Can not find jre folder");
    }

    private boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    private void parseUserClasspath(String cpOption) {
        if (StringUtils.isBlank(cpOption)) {
            cpOption = ".";
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
