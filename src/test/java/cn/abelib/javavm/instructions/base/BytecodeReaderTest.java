package cn.abelib.javavm.instructions.base;

import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.*;

/**
 * BytecodeReader 单元测试
 * @author abel.huang
 * @version 1.0
 * @date 2025/1/20
 */
public class BytecodeReaderTest {

    private BytecodeReader reader;

    @Before
    public void setUp() {
        reader = new BytecodeReader();
    }

    @Test
    public void testReadUInt8() {
        // 测试无符号 8 位整数读取
        byte[] code = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        reader.reset(code, 0);
        
        assertEquals("读取 0x00", 0, reader.readUInt8());
        assertEquals("读取 0x7F", 127, reader.readUInt8());
        assertEquals("读取 0x80", 128, reader.readUInt8());
        assertEquals("读取 0xFF", 255, reader.readUInt8());
    }

    @Test
    public void testReadInt8() {
        // 测试有符号 8 位整数读取
        byte[] code = {0x00, 0x7F, (byte) 0x80, (byte) 0xFF};
        reader.reset(code, 0);
        
        assertEquals("读取 0x00", 0, reader.readInt8());
        assertEquals("读取 0x7F", 127, reader.readInt8());
        assertEquals("读取 0x80", -128, reader.readInt8());
        assertEquals("读取 0xFF", -1, reader.readInt8());
    }

    @Test
    public void testReadInt16() {
        // 测试有符号 16 位整数读取（大端序）
        byte[] code = {0x00, 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00};
        reader.reset(code, 0);
        
        assertEquals("读取 0x0001", 1, reader.readInt16());
        assertEquals("读取 0xFFFF", -1, reader.readInt16());
        assertEquals("读取 0x8000", -32768, reader.readInt16());
    }

    @Test
    public void testReadUInt16() {
        // 测试无符号 16 位整数读取
        byte[] code = {0x00, 0x01, (byte) 0xFF, (byte) 0xFF, (byte) 0x80, 0x00};
        reader.reset(code, 0);
        
        assertEquals("读取 0x0001", 1, reader.readUInt16());
        assertEquals("读取 0xFFFF", 65535, reader.readUInt16());
        assertEquals("读取 0x8000", 32768, reader.readUInt16());
    }

    @Test
    public void testReadInt32() {
        // 测试有符号 32 位整数读取
        ByteBuffer buffer = ByteBuffer.allocate(12).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(1);
        buffer.putInt(-1);
        buffer.putInt(Integer.MAX_VALUE);
        
        reader.reset(buffer.array(), 0);
        
        assertEquals("读取 1", 1, reader.readInt32());
        assertEquals("读取 -1", -1, reader.readInt32());
        assertEquals("读取 MAX_VALUE", Integer.MAX_VALUE, reader.readInt32());
    }

    @Test
    public void testSkipPadding() {
        // 测试跳过填充字节
        byte[] code = {0x01, 0x02, 0x03, 0x04, 0x05};
        reader.reset(code, 1);
        
        reader.skipPadding();
        assertEquals("PC 应该对齐到 4", 4, reader.getPc());
        
        reader.reset(code, 0);
        reader.skipPadding();
        assertEquals("PC 已经对齐到 4", 0, reader.getPc());
    }

    @Test
    public void testReadInt32s() {
        // 测试读取多个 32 位整数
        ByteBuffer buffer = ByteBuffer.allocate(12).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(10);
        buffer.putInt(20);
        buffer.putInt(30);
        
        reader.reset(buffer.array(), 0);
        
        int[] ints = reader.readInt32s(3);
        assertArrayEquals("读取多个 int32", new int[]{10, 20, 30}, ints);
    }

    @Test
    public void testPcIncrement() {
        // 测试 PC 递增
        byte[] code = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
        reader.reset(code, 0);
        
        assertEquals("初始 PC", 0, reader.getPc());
        reader.readUInt8();
        assertEquals("读取 1 字节后 PC", 1, reader.getPc());
        reader.readUInt16();
        assertEquals("读取 2 字节后 PC", 3, reader.getPc());
        reader.readInt32();
        assertEquals("读取 4 字节后 PC", 7, reader.getPc());
    }

    @Test
    public void testSetPc() {
        byte[] code = {0x01, 0x02, 0x03, 0x04};
        reader.reset(code, 0);
        
        reader.setPc(2);
        assertEquals("设置 PC 后", 2, reader.getPc());
        
        assertEquals("从新 PC 位置读取", 0x03, reader.readUInt8());
    }
}
