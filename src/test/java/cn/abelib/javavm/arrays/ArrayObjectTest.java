package cn.abelib.javavm.arrays;

import cn.abelib.javavm.Classpath;
import cn.abelib.javavm.runtime.heap.ClassLoader;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static cn.abelib.javavm.instructions.references.ArrayTypeConstants.AT_INT;
import static org.junit.Assert.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/13 0:21
 */
public class ArrayObjectTest {

    @Test
    public void testArrayObject() throws IOException {
        // 初始化 Classpath 和 ClassLoader
        String javaHome = System.getProperty("java.home");
        String projectPath = System.getProperty("user.dir");
        
        Classpath classpath = new Classpath();
        classpath.parse(javaHome, projectPath + "/target/test-classes");
        
        ClassLoader classLoader = new ClassLoader(classpath, false);
        
        // 测试创建 int 数组
        Clazz arrClass = getPrimitiveArrayClass(classLoader, AT_INT);
        assertNotNull("数组类不应为 null", arrClass);
        
        // 创建数组对象
        JvmObject arr = arrClass.newArray(10);
        assertNotNull("数组对象不应为 null", arr);
        assertEquals("数组长度应为 10", 10, arr.getArrayLength());
        
        List<Integer> ints = arr.getInts();
        assertNotNull("int 列表不应为 null", ints);
        assertEquals("int 列表长度应为 10", 10, ints.size());
    }

    private Clazz getPrimitiveArrayClass(ClassLoader classLoader, int atype) throws IOException {
        switch (atype) {
            case 4: // AT_BOOLEAN
                return classLoader.loadClass("[Z");
            case 5: // AT_CHAR
                return classLoader.loadClass("[C");
            case 6: // AT_FLOAT
                return classLoader.loadClass("[F");
            case 7: // AT_DOUBLE
                return classLoader.loadClass("[D");
            case 8: // AT_BYTE
                return classLoader.loadClass("[B");
            case 9: // AT_SHORT
                return classLoader.loadClass("[S");
            case 10: // AT_INT
                return classLoader.loadClass("[I");
            case 11: // AT_LONG
                return classLoader.loadClass("[J");
            default:
                throw new RuntimeException("Invalid atype!");
        }
    }
}
