package cn.abelib.javavm;

import com.google.common.collect.Lists;
import org.apache.commons.cli.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/28 23:41
 */
public class Command {

    private boolean helpFlag;

    private boolean versionFlag;

    private String cpOption;

    private String xJreOption;

    private String clazz;

    private List<String> args;

    public Command parseCmd(String[] args) {
        final CommandLineParser parser = new DefaultParser();
        final Options options = new Options();
        options.addOption("?","help", false, "print help message");
        options.addOption("version", false, "print the version information and exit");
        options.addOption("cp", "classpath", true, "classpath");
        options.addOption("t", false, "display current time");
        options.addOption("Xjre", "Xjre", true, "path to jre");

        final CommandLine line;
        HelpFormatter formatter = new HelpFormatter();
        Command command = new Command();
        try {
            line = parser.parse(options, args);
            List<String> argList = line.getArgList();
            if (argList.size() > 0) {
                command.setClazz(argList.get(0));
                command.setArgs(Lists.newArrayList(argList.subList(1, argList.size() - 1)));
            }
            if (line.hasOption('t')) {
                System.err.println(LocalDateTime.now());
            } else if (line.hasOption("help") || line.hasOption("?")) {
                formatter.printHelp("?", options, true);
            } else if (line.hasOption("version")) {
                System.err.println("version 0.0.1");
            }
        } catch (ParseException e) {
            formatter.printHelp("?", options, true);
        }
        return command;
    }

    public boolean isHelpFlag() {
        return helpFlag;
    }

    public void setHelpFlag(boolean helpFlag) {
        this.helpFlag = helpFlag;
    }

    public boolean isVersionFlag() {
        return versionFlag;
    }

    public void setVersionFlag(boolean versionFlag) {
        this.versionFlag = versionFlag;
    }

    public String getCpOption() {
        return cpOption;
    }

    public void setCpOption(String cpOption) {
        this.cpOption = cpOption;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public String getXJreOption() {
        return xJreOption;
    }

    public void setXJreOption(String xJreOption) {
        this.xJreOption = xJreOption;
    }
}
