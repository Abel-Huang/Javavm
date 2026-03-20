package cn.abelib.javavm.runtime;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 21:25
 */
public class FrameTest {

    @Test
    public void testFrame() {
        Frame frame = new Frame(100, 100);
        assertNotNull("Frame 不应为 null", frame);
        assertNotNull("LocalVars 不应为 null", frame.getLocalVars());
        assertNotNull("OperandStack 不应为 null", frame.getOperandStack());
    }

    @Test
    public void testLocalVars() {
        Frame frame = new Frame(100, 100);
        LocalVars vars = frame.getLocalVars();
        
        // 测试 int 类型
        vars.setInt(0, 100);
        vars.setInt(1, -100);
        assertEquals("int 存储和读取", 100, vars.getInt(0));
        assertEquals("负数 int 存储和读取", -100, vars.getInt(1));
        
        // 测试 long 类型
        vars.setLong(2, 2997924580L);
        vars.setLong(4, -2997924580L);
        assertEquals("long 存储和读取", 2997924580L, vars.getLong(2));
        assertEquals("负数 long 存储和读取", -2997924580L, vars.getLong(4));
        
        // 测试 float 类型
        vars.setFloat(6, 3.1415926F);
        assertEquals("float 存储和读取", 3.1415926F, vars.getFloat(6), 0.0001F);
        
        // 测试 double 类型
        vars.setDouble(7, 2.71828182845);
        assertEquals("double 存储和读取", 2.71828182845, vars.getDouble(7), 0.0000001);
        
        // 测试引用类型
        vars.setRef(9, null);
        assertNull("引用存储和读取", vars.getRef(9));
    }

    @Test
    public void testOperandStack() {
        Frame frame = new Frame(100, 100);
        OperandStack ops = frame.getOperandStack();
        
        // 测试 int 类型栈操作
        ops.pushInt(100);
        ops.pushInt(-100);
        assertEquals("int 出栈顺序", -100, ops.popInt());
        assertEquals("int 出栈顺序", 100, ops.popInt());
        
        // 测试 long 类型栈操作
        ops.pushLong(2997924580L);
        ops.pushLong(-2997924580L);
        assertEquals("long 出栈顺序", -2997924580L, ops.popLong());
        assertEquals("long 出栈顺序", 2997924580L, ops.popLong());
        
        // 测试 float 类型栈操作
        ops.pushFloat(3.1415926F);
        assertEquals("float 出栈", 3.1415926F, ops.popFloat(), 0.0001F);
        
        // 测试 double 类型栈操作
        ops.pushDouble(2.71828182845);
        assertEquals("double 出栈", 2.71828182845, ops.popDouble(), 0.0000001);
        
        // 测试引用类型栈操作
        ops.pushRef(null);
        assertNull("引用出栈", ops.popRef());
    }

    @Test
    public void testOperandStackMixedTypes() {
        Frame frame = new Frame(100, 100);
        OperandStack ops = frame.getOperandStack();
        
        // 混合类型操作
        ops.pushInt(100);
        ops.pushLong(2997924580L);
        ops.pushFloat(3.14F);
        
        assertEquals("float 出栈", 3.14F, ops.popFloat(), 0.01F);
        assertEquals("long 出栈", 2997924580L, ops.popLong());
        assertEquals("int 出栈", 100, ops.popInt());
    }
}
