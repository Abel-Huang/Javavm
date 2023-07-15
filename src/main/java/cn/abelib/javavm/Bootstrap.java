package cn.abelib.javavm;

import cn.abelib.javavm.clazz.ClassFile;
import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.Method;
import cn.abelib.javavm.runtime.heap.ClassLoader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/9 23:56
 */
public class Bootstrap {

    public static void main(String[] args) throws IOException {
        start(args);
    }

    public static void start(String[] args) throws IOException {
        Command command = new Command();
        command.parseCmd(args);
        startJVM(command);
    }

    public static void startJVM(Command cmd) throws IOException {
        System.err.println("start JVM");
        Classpath classpath = new Classpath();
        classpath.parse(cmd.getXJreOption(), cmd.getCpOption());
        ClassLoader classLoader = new ClassLoader(classpath, cmd.isVerboseClassFlag());
        String className = cmd.getClazz().replace(".", "/");
        ClassFile cf = loadClass(className, classpath);
        Clazz clazz = classLoader.loadClass(className);
        printClassInfo(cf);
        Method mainMethod = clazz.getMainMethod();
        if(Objects.nonNull(mainMethod)) {
            Interpreter.interpret(mainMethod, cmd.isVerboseInstFlag());
        } else {
            System.out.printf("Main method not found in class %s%n", cmd.getClazz());
        }
    }

    private static ClassFile loadClass(String className , Classpath cp) throws IOException {
        byte[] data = cp.readClass(className);
        ClassFile classFile = new ClassFile();
        classFile.parse(data);
        return classFile;
    }

    private static void printClassInfo(ClassFile cf){
        System.out.printf("version:%s.%s%n", cf.getMajorVersion(), cf.getMinorVersion());
        System.out.printf("constantscount:%d%n", cf.getConstantPool().getConstantInfos().length);
        System.out.printf("accessflags:%d%n", cf.getAccessFlags());
        System.out.printf("thisclass:%s%n", cf.getClassName());
        System.out.printf("superclass:%s%n", cf.getSuperClassName());
        System.out.printf("interfaces:%s%n", Arrays.toString(cf.getInterfaceNames()));
        System.out.printf("fieldscount:%d%n", cf.getFields().length);
        System.out.printf("methodscount:%d%n", cf.getMethods().length);
        for (MemberInfo memberInfo : cf.getFields()) {
            System.out.printf("%s%n", memberInfo.getName());
        }
         for (MemberInfo memberInfo : cf.getMethods()) {
            System.out.printf("%s%n", memberInfo.getName());
         }
    }
}
