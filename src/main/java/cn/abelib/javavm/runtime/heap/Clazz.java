package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.ClassFile;
import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;
import cn.abelib.javavm.runtime.LocalVars;
import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.Map;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/12 23:50
 */
public class Clazz {
    private int accessFlags;
    /**
     * current class name
     */
    private String name;
    /**
     * supper class name
     */
    private String superClassName;
    private String[] interfaceNames;
    private RuntimeConstantPool constantPool;
    private Field[] fields;
    private Method[] methods;
    private ClassLoader classLoader;
    private Clazz superClass;
    private Clazz[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private LocalVars staticVars;
    private boolean initStarted;

    public Clazz(ClassFile classfile) {
        this.accessFlags = classfile.getAccessFlags();
        this.name = classfile.getClassName();
        this.superClassName = classfile.getSuperClassName();
        this.interfaceNames = classfile.getInterfaceNames();
        this.constantPool = new RuntimeConstantPool(this, classfile.getConstantPool());
        this.fields = newFields(this, classfile.getFields());
        this.methods = newMethods(this, classfile.getMethods());
    }

    public Clazz() {}

    public JvmObject newArray(int count) {
        if (!this.isArray()) {
            throw new RuntimeException("Not array class: " + this.name);
        }
        switch (this.getName()) {
            case "[Z":
                return new JvmObject(this, count);
            case "[B":
                return new JvmObject(this,count);
            case "[C":
                return new JvmObject(this, count);
            case "[S":
                return new JvmObject(this, count);
            case "[I":
                return new JvmObject(this, count);
            case "[J":
                return new JvmObject(this, count);
            case "[F":
                return new JvmObject(this, count);
            case "[D":
                return new JvmObject(this, count);
            default:
                return new JvmObject(this, count);
        }
    }

    /**
     * is array class
     * @return
     */
    public boolean isArray() {
        return this.getName().charAt(0) == '[';
    }

    private Method[] newMethods(Clazz clazz, MemberInfo[] memberInfos) {
        int len = memberInfos.length;
        this.methods = new Method[len];
        for(int i = 0; i < len; i ++) {
            MemberInfo memberInfo = memberInfos[i];
            methods[i] = new Method(clazz, memberInfo);
        }
        return methods;
    }

    private Field[] newFields(Clazz clazz, MemberInfo[] memberInfos) {
        int len = memberInfos.length;
        Field[] fields = new Field[len];
        for(int i = 0; i < len; i ++) {
            MemberInfo memberInfo = memberInfos[i];
            fields[i] = new Field(clazz, memberInfo);
        }
        return fields;
    }

    private ConstantPool newConstantPool(Clazz clazz, ConstantPool constantPool) {
        return null;
    }

    public boolean isPublic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & AccessFlags.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & AccessFlags.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & AccessFlags.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SUPER);
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & AccessFlags.ACC_SYNCHRONIZED);
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & AccessFlags.ACC_VOLATILE);
    }

    public boolean isInterface() {
        return 0 != (this.accessFlags & AccessFlags.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & AccessFlags.ACC_ABSTRACT);
    }

    public boolean isJlObject() {
        return "java/lang/Object".equals(this.name);
    }

    public boolean isJlCloneable() {
        return "java/lang/Cloneable".equals(this.name);
    }

    public boolean isJioSerializable() {
        return "java/io/Serializable".equals(this.name);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public void setSuperClassName(String superClassName) {
        this.superClassName = superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public void setInterfaceNames(String[] interfaceNames) {
        this.interfaceNames = interfaceNames;
    }

    public Clazz getSuperClass() {
        return superClass;
    }

    public void setSuperClass(Clazz superClass) {
        this.superClass = superClass;
    }

    public Clazz[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(Clazz[] interfaces) {
        this.interfaces = interfaces;
    }

    public int getInstanceSlotCount() {
        return instanceSlotCount;
    }

    public void setInstanceSlotCount(int instanceSlotCount) {
        this.instanceSlotCount = instanceSlotCount;
    }

    public int getStaticSlotCount() {
        return staticSlotCount;
    }

    public void setStaticSlotCount(int staticSlotCount) {
        this.staticSlotCount = staticSlotCount;
    }

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Method[] getMethods() {
        return methods;
    }

    public void setMethods(Method[] methods) {
        this.methods = methods;
    }

    public LocalVars getStaticVars() {
        return staticVars;
    }

    public void setStaticVars(LocalVars staticVars) {
        this.staticVars = staticVars;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(int accessFlags) {
        this.accessFlags = accessFlags;
    }

    public RuntimeConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(RuntimeConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public boolean isInitStarted() {
        return initStarted;
    }

    public void setInitStarted(boolean initStarted) {
        this.initStarted = initStarted;
    }

    public String getPackageName() {
        // todo code fix
        int i = this.name.lastIndexOf( "/");
        if (i >= 0) {
            return this.name.substring(0, i);
        }
        return "";
    }

    public boolean isAccessibleTo(Clazz clazz) {
        return this.isPublic() || this.getPackageName().equals(clazz.getPackageName());
    }

    public JvmObject newObject() {
        return new JvmObject(this);
    }

    /**
     * this extends c
     * @param other
     * @return
     */
    public boolean isSubClassOf(Clazz other) {
        for (Clazz c = this.superClass; c != null; c = c.superClass) {
            // todo equal fix
            if (c == other) {
                return true;
            }
        }
        return false;
    }

    /**
     * c extends self
     * @param other
     * @return
     */
    public boolean isSuperClassOf(Clazz other) {
        return other.isSubClassOf(other);
    }

    /**
     * iface extends self
     * @param iface
     * @return
     */
    public boolean isSuperInterfaceOf(Clazz iface) {
        return iface.isSubInterfaceOf(this);
    }

    public boolean isImplements(Clazz other) {
       for (Clazz c = this; c != null; c = c.superClass) {
            for (Clazz iface : c.interfaces) {
                if (iface == other  || iface.isSubInterfaceOf(other)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * self extends iface
     * @return
     */
    private boolean isSubInterfaceOf(Clazz other) {
        for (Clazz superInterface : other.interfaces) {
            if (superInterface == other || superInterface.isSubInterfaceOf(other)) {
                return true;
            }
        }
        return false;
    }

    public Method getMainMethod() {
        return this.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    private Method getStaticMethod(String name, String descriptor) {
        for (Method method : this.methods) {
            if(method.isStatic()
                    && method.getName().equals(name)
                    && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    public boolean isAssignableFrom(Clazz otherClazz) throws IOException {
        if (this == otherClazz) {
            return true;
        }
        if (!otherClazz.isArray()) {
            if (!otherClazz.isInterface()) {
                if (!this.isInterface()) {
                    return otherClazz.isSubClassOf(this);
                } else {
                    return otherClazz.isImplements(this);
                }
            } else {
                if (!this.isInterface()) {
                    return this.isJlObject();
                } else {
                    return this.isSuperInterfaceOf(otherClazz);
                }
            }
        } else {
            if (!this.isArray()) {
                if (!this.isInterface()) {
                    return this.isJlObject();
                } else {
                    return this.isJlCloneable() || this.isJioSerializable();
                }
            } else {
                Clazz sc = otherClazz.getComponentClass();
                Clazz tc = this.getComponentClass();
                return sc == tc || tc.isAssignableFrom(sc);
            }
        }
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "name='" + name + '\'' +
                '}';
    }

    public Clazz arrayClass() throws IOException {
        String arrayClassName = getArrayClassName(this.name);
        return this.classLoader.loadClass(arrayClassName);
    }

    private String getArrayClassName(String name) {
        return "[" + toDescriptor(name);
    }

    private String toDescriptor(String name) {
        if (name.charAt(0) == '[') {
            return name;
        }
        if (primitiveTypes.containsKey(name)) {
            return primitiveTypes.get(name);
        }
        return "L" + name + ";";
    }

    private static Map<String, String> primitiveTypes = Maps.newHashMap();

    static {
        primitiveTypes.put("void", "V");
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
    }

    public Clazz getComponentClass() throws IOException {
        String componentClassName = getComponentClassName(this.name);
        return this.classLoader.loadClass(componentClassName);

    }

    private String getComponentClassName(String name) {
        if (name.charAt(0) == '[') {
            String componentTypeDescriptor = name.substring(1);
            return toClassName(componentTypeDescriptor);
        }
        throw new RuntimeException("Not array: " + name);
    }

    private String toClassName(String descriptor) {
        // array
        if (descriptor.charAt(0) == '[') {
            return descriptor;
        }
        // object
        if (descriptor.charAt(0) == 'L') {
            // todo 确认下取值范围
            return descriptor.substring(1);
        }
        // primitive
        for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
            if (entry.getValue().equals(descriptor))
            return entry.getKey();
        }
        throw new RuntimeException("Invalid descriptor: " + descriptor);
    }
}
