package cn.abelib.javavm;

import cn.abelib.javavm.clazz.ClassFile;
import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.instructions.base.Interpreter;
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
        ClassLoader classLoader = new ClassLoader(classpath);
        String className = cmd.getClazz().replace(".", "/");
        ClassFile cf = loadClass(className, classpath);
        Clazz clazz = classLoader.loadClass(className);
        printClassInfo(cf);
        Method mainMethod = clazz.getMainMethod();
        if(Objects.nonNull(mainMethod)) {
            Interpreter.interpret(mainMethod);
        } else {
            System.out.println(String.format("Main method not found in class %s\n", cmd.getClazz()));
        }
    }

    private static ClassFile loadClass(String className , Classpath cp) throws IOException {
        byte[] data = cp.readClass(className);
        ClassFile classFile = new ClassFile();
        classFile.parse(data);
        return classFile;
    }

    private static void printClassInfo(ClassFile cf){
        System.out.println(String.format("version:%s.%s", cf.getMajorVersion(), cf.getMinorVersion()));
        System.out.println(String.format("constantscount:%s", cf.getConstantPool().getConstantInfos().length));
        System.out.println(String.format("accessflags:0x%s", cf.getAccessFlags()));
        System.out.println(String.format("thisclass:%s", cf.getClassName()));
        System.out.println(String.format("superclass:%s", cf.getSuperClassName()));
        System.out.println(String.format("interfaces:%s", Arrays.toString(cf.getInterfaceNames())));
        System.out.println(String.format("fieldscount:%s", cf.getFields().length));
        System.out.println(String.format("methodscount:%s", cf.getMethods().length));
        for (MemberInfo memberInfo : cf.getFields()) {
            System.out.println(String.format("%s", memberInfo.getName()));
        }
         for (MemberInfo memberInfo : cf.getMethods()) {
            System.out.println(String.format("%s", memberInfo.getName()));
         }
    }
}
