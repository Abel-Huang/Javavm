package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.Classpath;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * ClassLoader 单元测试
 * @author abel.huang
 * @version 1.0
 * @date 2025/1/20
 */
public class ClassLoaderTest {

    private ClassLoader classLoader;

    @Before
    public void setUp() {
        Classpath classpath = new Classpath();
        String javaHome = System.getProperty("java.home");
        classpath.parse(javaHome, "");
        classLoader = new ClassLoader(classpath, false);
    }

    @Test
    public void testLoadJavaLangObject() throws IOException {
        Clazz objectClass = classLoader.loadClass("java/lang/Object");
        
        assertNotNull("Object 类不应为 null", objectClass);
        assertEquals("类名应为 java/lang/Object", "java/lang/Object", objectClass.getName());
        assertTrue("Object 应该是 public", objectClass.isPublic());
        assertFalse("Object 不是接口", objectClass.isInterface());
        assertFalse("Object 不是数组", objectClass.isArray());
        assertNull("Object 没有父类", objectClass.getSuperClass());
    }

    @Test
    public void testLoadJavaLangString() throws IOException {
        Clazz stringClass = classLoader.loadClass("java/lang/String");
        
        assertNotNull("String 类不应为 null", stringClass);
        assertEquals("类名应为 java/lang/String", "java/lang/String", stringClass.getName());
        assertTrue("String 应该是 public", stringClass.isPublic());
        assertTrue("String 应该是 final", stringClass.isFinal());
    }

    @Test
    public void testLoadPrimitiveClasses() throws IOException {
        // 测试基本类型是否已预加载
        Clazz voidClass = classLoader.loadClass("void");
        Clazz intClass = classLoader.loadClass("int");
        Clazz booleanClass = classLoader.loadClass("boolean");
        Clazz longClass = classLoader.loadClass("long");
        Clazz doubleClass = classLoader.loadClass("double");
        Clazz floatClass = classLoader.loadClass("float");
        Clazz byteClass = classLoader.loadClass("byte");
        Clazz shortClass = classLoader.loadClass("short");
        Clazz charClass = classLoader.loadClass("char");
        
        assertNotNull("void 类不应为 null", voidClass);
        assertNotNull("int 类不应为 null", intClass);
        assertNotNull("boolean 类不应为 null", booleanClass);
        assertNotNull("long 类不应为 null", longClass);
        assertNotNull("double 类不应为 null", doubleClass);
        assertNotNull("float 类不应为 null", floatClass);
        assertNotNull("byte 类不应为 null", byteClass);
        assertNotNull("short 类不应为 null", shortClass);
        assertNotNull("char 类不应为 null", charClass);
    }

    @Test
    public void testLoadPrimitiveByDescriptor() throws IOException {
        // 测试通过描述符加载基本类型
        Clazz voidClass = classLoader.loadClass("V");
        Clazz intClass = classLoader.loadClass("I");
        Clazz booleanClass = classLoader.loadClass("Z");
        Clazz longClass = classLoader.loadClass("J");
        
        assertEquals("V 应该映射到 void", "void", voidClass.getName());
        assertEquals("I 应该映射到 int", "int", intClass.getName());
        assertEquals("Z 应该映射到 boolean", "boolean", booleanClass.getName());
        assertEquals("J 应该映射到 long", "long", longClass.getName());
    }

    @Test
    public void testLoadArrayClass() throws IOException {
        // 测试加载一维数组
        Clazz intArrayClass = classLoader.loadClass("[I");
        
        assertNotNull("int[] 类不应为 null", intArrayClass);
        assertEquals("类名应为 [I", "[I", intArrayClass.getName());
        assertTrue("数组类应该是数组", intArrayClass.isArray());
        assertFalse("数组类不是接口", intArrayClass.isInterface());
    }

    @Test
    public void testLoadObjectArrayClass() throws IOException {
        // 测试加载对象数组
        Clazz stringArrayClass = classLoader.loadClass("[Ljava/lang/String;");
        
        assertNotNull("String[] 类不应为 null", stringArrayClass);
        assertEquals("类名应为 [Ljava/lang/String;", "[Ljava/lang/String;", stringArrayClass.getName());
        assertTrue("数组类应该是数组", stringArrayClass.isArray());
    }

    @Test
    public void testLoadMultiDimensionalArray() throws IOException {
        // 测试加载多维数组
        Clazz intArray2DClass = classLoader.loadClass("[[I");
        
        assertNotNull("int[][] 类不应为 null", intArray2DClass);
        assertEquals("类名应为 [[I", "[[I", intArray2DClass.getName());
        assertTrue("多维数组类应该是数组", intArray2DClass.isArray());
    }

    @Test
    public void testClassCache() throws IOException {
        // 测试类缓存
        Clazz class1 = classLoader.loadClass("java/lang/Object");
        Clazz class2 = classLoader.loadClass("java/lang/Object");
        
        assertSame("相同类应该返回同一实例", class1, class2);
    }

    @Test
    public void testJClass() throws IOException {
        // 测试 JClass 对象
        Clazz objectClass = classLoader.loadClass("java/lang/Object");
        JvmObject jClass = objectClass.getJClass();
        
        assertNotNull("JClass 不应为 null", jClass);
        assertNotNull("JClass 的 extra 应为 Clazz", jClass.getExtra());
        assertSame("JClass 的 extra 应该是原 Clazz", objectClass, jClass.getExtra());
    }

    @Test
    public void testPrimitiveJClass() throws IOException {
        // 测试基本类型的 JClass
        Clazz intClass = classLoader.loadClass("int");
        JvmObject jClass = intClass.getJClass();
        
        assertNotNull("基本类型的 JClass 不应为 null", jClass);
        assertSame("JClass 的 extra 应该是原 Clazz", intClass, jClass.getExtra());
    }

    @Test
    public void testArraySuperClass() throws IOException {
        // 测试数组的父类是 Object
        Clazz intArrayClass = classLoader.loadClass("[I");
        Clazz superClass = intArrayClass.getSuperClass();
        
        assertNotNull("数组应该有父类", superClass);
        assertEquals("数组的父类应该是 Object", "java/lang/Object", superClass.getName());
    }

    @Test
    public void testArrayInterfaces() throws IOException {
        // 测试数组实现的接口
        Clazz intArrayClass = classLoader.loadClass("[I");
        Clazz[] interfaces = intArrayClass.getInterfaces();
        
        assertNotNull("数组应该有接口", interfaces);
        assertTrue("数组应该实现 Cloneable 和 Serializable", interfaces.length >= 2);
    }

    @Test
    public void testClassIsPublic() throws IOException {
        Clazz objectClass = classLoader.loadClass("java/lang/Object");
        assertTrue("Object 应该是 public", objectClass.isPublic());
    }

    @Test
    public void testStringIsFinal() throws IOException {
        Clazz stringClass = classLoader.loadClass("java/lang/String");
        assertTrue("String 应该是 final", stringClass.isFinal());
    }

    @Test
    public void testRunnableIsInterface() throws IOException {
        Clazz runnableClass = classLoader.loadClass("java/lang/Runnable");
        assertTrue("Runnable 应该是接口", runnableClass.isInterface());
    }

    @Test
    public void testPackageName() throws IOException {
        Clazz stringClass = classLoader.loadClass("java/lang/String");
        String packageName = stringClass.getPackageName();
        
        assertEquals("包名应为 java/lang", "java/lang", packageName);
    }

    @Test
    public void testDefaultPackageName() throws IOException {
        Clazz intClass = classLoader.loadClass("int");
        String packageName = intClass.getPackageName();
        
        assertEquals("基本类型包名应为空", "", packageName);
    }
}
