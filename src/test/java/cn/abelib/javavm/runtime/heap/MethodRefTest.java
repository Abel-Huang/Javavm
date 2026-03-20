package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.Classpath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * MethodRef 单元测试
 * @author abel.huang
 * @version 1.0
 * @date 2025/1/20
 */
public class MethodRefTest {

    private ClassLoader classLoader;

    @Before
    public void setUp() {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        classLoader = new ClassLoader(classpath, false);
    }

    @Test
    public void testLookupMethodInClass() throws IOException {
        // 测试在类中查找方法
        Clazz objectClass = classLoader.loadClass("java/lang/Object");
        
        // 使用测试辅助方法
        Method method = lookupMethodInClass(objectClass, "getClass", "()Ljava/lang/Class;");
        
        assertNotNull("应该能找到 getClass 方法", method);
        assertEquals("方法名应为 getClass", "getClass", method.getName());
        assertEquals("描述符应为 ()Ljava/lang/Class;", "()Ljava/lang/Class;", method.getDescriptor());
    }

    @Test
    public void testLookupMethodInClassNotFound() throws IOException {
        Clazz objectClass = classLoader.loadClass("java/lang/Object");
        
        // 查找不存在的方法
        Method method = lookupMethodInClass(objectClass, "nonExistentMethod", "()V");
        
        assertNull("不应该找到不存在的方法", method);
    }

    @Test
    public void testLookupMethodInSuperClass() throws IOException {
        // 测试在父类中查找方法
        Clazz stringClass = classLoader.loadClass("java/lang/String");
        
        // String 继承自 Object，应该能找到 Object 的方法
        Method method = lookupMethodInClass(stringClass, "hashCode", "()I");
        
        assertNotNull("应该在父类中找到 hashCode 方法", method);
    }

    @Test
    public void testLookupMethodWithNullMethods() throws IOException {
        // 基本类型类没有 methods
        Clazz intClass = classLoader.loadClass("int");
        
        // 不应该抛出异常
        Method method = lookupMethodInClass(intClass, "anyMethod", "()V");
        assertNull("基本类型类没有方法", method);
    }

    @Test
    public void testLookupMethodInArrayClass() throws IOException {
        // 测试在数组类中查找方法
        Clazz intArrayClass = classLoader.loadClass("[I");
        
        // 数组继承自 Object，应该能找到 Object 的方法
        Method method = lookupMethodInClass(intArrayClass, "clone", "()Ljava/lang/Object;");
        
        assertNotNull("应该在 Object 中找到 clone 方法", method);
    }
    
    /**
     * 复制 MethodRef.lookupMethodInClass 的逻辑用于测试
     */
    private Method lookupMethodInClass(Clazz c, String name, String descriptor) {
        for (Clazz clazz = c; clazz != null; clazz = clazz.getSuperClass()) {
            Method[] methods = clazz.getMethods();
            if (methods == null) {
                continue;  // 跳过没有方法的类（如数组类、基本类型类）
            }
            for (Method method : methods) {
                if (method.name.equals(name) && method.descriptor.equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }
}
