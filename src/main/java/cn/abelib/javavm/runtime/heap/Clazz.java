package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.clazz.ClassFile;
import cn.abelib.javavm.clazz.MemberInfo;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;
import cn.abelib.javavm.runtime.LocalVars;

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

    public Clazz(ClassFile classfile) {
        this.accessFlags = classfile.getAccessFlags();
        this.name = classfile.getClassName();
        this.superClassName = classfile.getSuperClassName();
        this.interfaceNames = classfile.getInterfaceNames();
        this.constantPool = new RuntimeConstantPool(this, classfile.getConstantPool());
        this.fields = newFields(this, classfile.getFields());
        this.methods = newMethods(this, classfile.getMethods());
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
        return 0 != (this.accessFlags & Const.ACC_PUBLIC);
    }

    public boolean isPrivate() {
        return 0 != (this.accessFlags & Const.ACC_PRIVATE);
    }

    public boolean isProtected() {
        return 0 != (this.accessFlags & Const.ACC_PROTECTED);
    }

    public boolean isStatic() {
        return 0 != (this.accessFlags & Const.ACC_STATIC);
    }

    public boolean isFinal() {
        return 0 != (this.accessFlags & Const.ACC_FINAL);
    }

    public boolean isSuper() {
        return 0 != (this.accessFlags & Const.ACC_SUPER);
    }

    public boolean isSynchronized() {
        return 0 != (this.accessFlags & Const.ACC_SYNCHRONIZED);
    }

    public boolean isVolatile() {
        return 0 != (this.accessFlags & Const.ACC_VOLATILE);
    }

    public boolean isInterface() {
        return 0 != (this.accessFlags & Const.ACC_INTERFACE);
    }

    public boolean isAbstract() {
        return 0 != (this.accessFlags & Const.ACC_ABSTRACT);
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

    // self extends c
    public boolean isSubClassOf(Clazz other) {
        for (Clazz c = this.superClass; c != null; c = c.superClass) {
            // todo equal fix
            if (c == other) {
                return true;
            }
        }
        return false;
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

    public boolean isAssignableFrom(Clazz clazz) {
        if (this == clazz) {
            return true;
        }
        if (!this.isInterface()) {
            return clazz.isSubClassOf(this);
        } else {
            return clazz.isImplements(this);
        }
    }
}
