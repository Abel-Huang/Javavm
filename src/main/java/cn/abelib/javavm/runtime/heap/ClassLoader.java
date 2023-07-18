package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.Classpath;
import cn.abelib.javavm.clazz.ClassFile;
import cn.abelib.javavm.runtime.LocalVars;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/13 0:30
 */
public class ClassLoader {
    private Classpath classpath;
    private boolean verboseClassFlag;
    private Map<String, Clazz> clazzMap;

    public ClassLoader(Classpath cp, boolean verboseClassFlag) {
        this.classpath = cp;
        this.clazzMap = Maps.newHashMap();
    }

    public Clazz loadClass(String name) throws IOException {
        if (clazzMap.containsKey(name)) {
            // 类已经加载
            return clazzMap.get(name);
        }
        if (name.charAt(0) == '[') {
            return this.loadArrayClass(name);
        }
        return this.loadNonArrayClass(name);
    }

    /**
     * load array class
     * @param name
     * @return
     */
    private Clazz loadArrayClass(String name) throws IOException {
        Clazz clazz = new Clazz();
        clazz.setAccessFlags(AccessFlags.ACC_PUBLIC);
        clazz.setName(name);
        clazz.setClassLoader(this);
        Clazz superClass =  this.loadClass("java/lang/Object");
        clazz.setSuperClass(superClass);
        clazz.setSuperClassName(superClass.getName());
        Clazz interfaceClass1 =  this.loadClass("java/lang/Cloneable");
        Clazz interfaceClass2 =  this.loadClass("java/io/Serializable");
        Clazz[] interfaces = new Clazz[]{interfaceClass1, interfaceClass2};
        clazz.setInterfaces(interfaces);
        String[] interfaceNames = new String[]{interfaceClass1.getName(), interfaceClass2.getName()};
        clazz.setInterfaceNames(interfaceNames);
        clazz.setInitStarted(true);
        this.clazzMap.put(clazz.getName(), clazz);
        return clazz;
    }

    /**
     * load non array class
     * @param name
     * @return
     * @throws IOException
     */
    private Clazz loadNonArrayClass(String name) throws IOException {
        byte[] data = this.readClass(name);
        Clazz clazz = this.defineClass(data);
        link(clazz);
        if (this.verboseClassFlag) {
            System.out.printf("[Loaded class from %s]", name);
        }

        return clazz;
    }

    private void link(Clazz clazz) {
        verify(clazz);
        prepare(clazz);
    }

    private void verify(Clazz clazz) {
        // todo
    }

    private void prepare(Clazz clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * allocate and init static variables
     * @param clazz
     */
    private void allocAndInitStaticVars(Clazz clazz) {

        clazz.setStaticVars(new LocalVars(clazz.getStaticSlotCount()));

        for(Field field : clazz.getFields()) {
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVar(clazz, field);
            }
        }
    }

    private void initStaticFinalVar(Clazz clazz, Field field) {
        LocalVars vars = clazz.getStaticVars();
        RuntimeConstantPool constantPool = clazz.getConstantPool();
        int cpIndex = field.getConstValueIndex();
        int slotId = field.getSlotId();
        if (cpIndex > 0) {
            switch (field.getDescriptor()) {
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                   int intVal = constantPool.getConstant(cpIndex).getIntValue();
                   vars.setInt(slotId, intVal);
                    break;
                case "J":
                    long longVal = constantPool.getConstant(cpIndex).getLongValue();
                    vars.setLong(slotId, longVal);
                    break;
                case "F":
                    float floatVal = constantPool.getConstant(cpIndex).getFloatValue();
                    vars.setFloat(slotId, floatVal);
                    break;
                case "D":
                    double doubleVal = constantPool.getConstant(cpIndex).getDoubleValue();
                    vars.setDouble(slotId, doubleVal);
                    break;
                case "Ljava/lang/String;":
                    // support string
                    String str = constantPool.getConstant(cpIndex).getStringValue();
                    if (Objects.isNull(str)) {
                        break;
                    }
                    JvmObject jStr = StringPool.getString(clazz.getClassLoader(), str);
                    vars.setRef(slotId, jStr);
            }
        }
    }

    /**
     * static field count
     * @param clazz
     */
    private void calcStaticFieldSlotIds(Clazz clazz) {
        int slotId = 0;

        for(Field field : clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    /**
     * instance field count
     * @param clazz
     */
    private void calcInstanceFieldSlotIds(Clazz clazz) {
        int slotId = 0;
        if(Objects.nonNull(clazz.getSuperClass()))  {
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for(Field field : clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()) {
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    private Clazz defineClass(byte[] data) throws IOException {
        Clazz clazz = parseClass(data);
        clazz.setClassLoader(this);
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        this.clazzMap.put(clazz.getName(), clazz);
        return clazz;
    }

    private void resolveInterfaces(Clazz clazz) throws IOException {
        int interfaceCount = clazz.getInterfaceNames().length;
        if (interfaceCount > 0) {
            Clazz[] interfaces = new Clazz[interfaceCount];
            for (int i = 0; i < interfaceCount; i ++) {
                Clazz interfaceClazz = clazz.getClassLoader().loadClass(clazz.getInterfaceNames()[i]);
                interfaces[i] = interfaceClazz;
            }
            clazz.setInterfaces(interfaces);
        }
    }

    /**
     * except Object
     * @param clazz
     * @throws IOException
     */
    private void resolveSuperClass(Clazz clazz) throws IOException {
        if(!clazz.getName().equals("java/lang/Object")) {
            Clazz supperClazz = clazz.getClassLoader().loadClass(clazz.getSuperClassName());
            clazz.setSuperClassName(supperClazz.getName());
            clazz.setSuperClass(supperClazz);
        }
    }

    private Clazz parseClass(byte[] data) throws IOException {
        ClassFile classFile = new ClassFile();
        classFile.parse(data);
        return new Clazz(classFile);
    }

    /**
     * just call classpath
     * @param name
     * @return
     * @throws IOException
     */
    private byte[] readClass(String name) throws IOException {
        return this.classpath.readClass(name);
    }
}
