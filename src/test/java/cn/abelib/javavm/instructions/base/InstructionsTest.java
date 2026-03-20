package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.instructions.comparisons.*;
import cn.abelib.javavm.instructions.constants.*;
import cn.abelib.javavm.instructions.controls.*;
import cn.abelib.javavm.instructions.conversions.*;
import cn.abelib.javavm.instructions.extended.*;
import cn.abelib.javavm.instructions.loads.*;
import cn.abelib.javavm.instructions.maths.*;
import cn.abelib.javavm.instructions.references.*;
import cn.abelib.javavm.instructions.reserved.InvokeNative;
import cn.abelib.javavm.instructions.stacks.*;
import cn.abelib.javavm.instructions.stores.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Instructions 指令工厂测试
 * @author abel.huang
 * @version 1.0
 * @date 2025/1/20
 */
public class InstructionsTest {

    // ==================== 常量指令测试 ====================
    
    @Test
    public void testNop() {
        Instruction inst = Instructions.newInstruction(0x00);
        assertTrue("NOP 应该是 Nop 类型", inst instanceof Nop);
    }

    @Test
    public void testAConstNull() {
        Instruction inst = Instructions.newInstruction(0x01);
        assertTrue("ACONST_NULL 应该是 AConstNull 类型", inst instanceof AConstNull);
    }

    @Test
    public void testIntegerConst() {
        assertTrue("ICONST_M1", Instructions.newInstruction(0x02) instanceof IntegerConstM1);
        assertTrue("ICONST_0", Instructions.newInstruction(0x03) instanceof IntegerConst0);
        assertTrue("ICONST_1", Instructions.newInstruction(0x04) instanceof IntegerConst1);
        assertTrue("ICONST_2", Instructions.newInstruction(0x05) instanceof IntegerConst2);
        assertTrue("ICONST_3", Instructions.newInstruction(0x06) instanceof IntegerConst3);
        assertTrue("ICONST_4", Instructions.newInstruction(0x07) instanceof IntegerConst4);
        assertTrue("ICONST_5", Instructions.newInstruction(0x08) instanceof IntegerConst5);
    }

    @Test
    public void testLongConst() {
        assertTrue("LCONST_0", Instructions.newInstruction(0x09) instanceof LongConst0);
        assertTrue("LCONST_1", Instructions.newInstruction(0x0a) instanceof LongConst1);
    }

    @Test
    public void testFloatConst() {
        assertTrue("FCONST_0", Instructions.newInstruction(0x0b) instanceof FloatConst0);
        assertTrue("FCONST_1", Instructions.newInstruction(0x0c) instanceof FloatConst1);
        assertTrue("FCONST_2", Instructions.newInstruction(0x0d) instanceof FloatConst2);
    }

    @Test
    public void testDoubleConst() {
        assertTrue("DCONST_0", Instructions.newInstruction(0x0e) instanceof DoubleConst0);
        assertTrue("DCONST_1", Instructions.newInstruction(0x0f) instanceof DoubleConst1);
    }

    // ==================== 加载指令测试 ====================

    @Test
    public void testIntegerLoad() {
        assertTrue("ILOAD_0", Instructions.newInstruction(0x1a) instanceof IntegerLoad0);
        assertTrue("ILOAD_1", Instructions.newInstruction(0x1b) instanceof IntegerLoad1);
        assertTrue("ILOAD_2", Instructions.newInstruction(0x1c) instanceof IntegerLoad2);
        assertTrue("ILOAD_3", Instructions.newInstruction(0x1d) instanceof IntegerLoad3);
    }

    @Test
    public void testRefLoad() {
        assertTrue("ALOAD_0", Instructions.newInstruction(0x2a) instanceof RefLoad0);
        assertTrue("ALOAD_1", Instructions.newInstruction(0x2b) instanceof RefLoad1);
        assertTrue("ALOAD_2", Instructions.newInstruction(0x2c) instanceof RefLoad2);
        assertTrue("ALOAD_3", Instructions.newInstruction(0x2d) instanceof RefLoad3);
    }

    // ==================== 存储指令测试 ====================

    @Test
    public void testIntegerStore() {
        assertTrue("ISTORE_0", Instructions.newInstruction(0x3b) instanceof IntegerStore0);
        assertTrue("ISTORE_1", Instructions.newInstruction(0x3c) instanceof IntegerStore1);
        assertTrue("ISTORE_2", Instructions.newInstruction(0x3d) instanceof IntegerStore2);
        assertTrue("ISTORE_3", Instructions.newInstruction(0x3e) instanceof IntegerStore3);
    }

    @Test
    public void testRefStore() {
        assertTrue("ASTORE_0", Instructions.newInstruction(0x4b) instanceof RefStore0);
        assertTrue("ASTORE_1", Instructions.newInstruction(0x4c) instanceof RefStore1);
        assertTrue("ASTORE_2", Instructions.newInstruction(0x4d) instanceof RefStore2);
        assertTrue("ASTORE_3", Instructions.newInstruction(0x4e) instanceof RefStore3);
    }

    // ==================== 栈操作指令测试 ====================

    @Test
    public void testStackOperations() {
        assertTrue("POP", Instructions.newInstruction(0x57) instanceof Pop);
        assertTrue("POP2", Instructions.newInstruction(0x58) instanceof Pop2);
        assertTrue("DUP", Instructions.newInstruction(0x59) instanceof Duplicate);
        assertTrue("DUP_X1", Instructions.newInstruction(0x5a) instanceof DuplicateX1);
        assertTrue("DUP_X2", Instructions.newInstruction(0x5b) instanceof DuplicateX2);
        assertTrue("DUP2", Instructions.newInstruction(0x5c) instanceof Duplicate2);
        assertTrue("SWAP", Instructions.newInstruction(0x5f) instanceof Swap);
    }

    // ==================== 数学运算指令测试 ====================

    @Test
    public void testMathAdd() {
        assertTrue("IADD", Instructions.newInstruction(0x60) instanceof IntegerAdd);
        assertTrue("LADD", Instructions.newInstruction(0x61) instanceof LongAdd);
        assertTrue("FADD", Instructions.newInstruction(0x62) instanceof FloatAdd);
        assertTrue("DADD", Instructions.newInstruction(0x63) instanceof DoubleAdd);
    }

    @Test
    public void testMathSub() {
        assertTrue("ISUB", Instructions.newInstruction(0x64) instanceof IntegerSub);
        assertTrue("LSUB", Instructions.newInstruction(0x65) instanceof LongSub);
        assertTrue("FSUB", Instructions.newInstruction(0x66) instanceof FloatSub);
        assertTrue("DSUB", Instructions.newInstruction(0x67) instanceof DoubleSub);
    }

    @Test
    public void testMathMul() {
        assertTrue("IMUL", Instructions.newInstruction(0x68) instanceof IntegerMulti);
        assertTrue("LMUL", Instructions.newInstruction(0x69) instanceof LongMulti);
        assertTrue("FMUL", Instructions.newInstruction(0x6a) instanceof FloatMulti);
        assertTrue("DMUL", Instructions.newInstruction(0x6b) instanceof DoubleMulti);
    }

    @Test
    public void testMathDiv() {
        assertTrue("IDIV", Instructions.newInstruction(0x6c) instanceof IntegerDivide);
        assertTrue("LDIV", Instructions.newInstruction(0x6d) instanceof LongDivide);
        assertTrue("FDIV", Instructions.newInstruction(0x6e) instanceof FloatDivide);
        assertTrue("DDIV", Instructions.newInstruction(0x6f) instanceof DoubleDivide);
    }

    // ==================== 类型转换指令测试 ====================

    @Test
    public void testConversion() {
        assertTrue("I2L", Instructions.newInstruction(0x85) instanceof Integer2Long);
        assertTrue("I2F", Instructions.newInstruction(0x86) instanceof Integer2Float);
        assertTrue("I2D", Instructions.newInstruction(0x87) instanceof Integer2Double);
        assertTrue("L2I", Instructions.newInstruction(0x88) instanceof Long2Integer);
        assertTrue("F2I", Instructions.newInstruction(0x8b) instanceof Float2Integer);
        assertTrue("D2I", Instructions.newInstruction(0x8e) instanceof Double2Integer);
    }

    // ==================== 比较指令测试 ====================

    @Test
    public void testComparison() {
        assertTrue("LCMP", Instructions.newInstruction(0x94) instanceof LongCompare);
        assertTrue("FCMPL", Instructions.newInstruction(0x95) instanceof FloatCompareLow);
        assertTrue("FCMPG", Instructions.newInstruction(0x96) instanceof FloatCompareGreat);
        assertTrue("DCMPL", Instructions.newInstruction(0x97) instanceof DoubleCompareLow);
        assertTrue("DCMPG", Instructions.newInstruction(0x98) instanceof DoubleCompareGreat);
    }

    // ==================== 控制流指令测试 ====================

    @Test
    public void testReturn() {
        assertTrue("IRETURN", Instructions.newInstruction(0xac) instanceof IntegerReturn);
        assertTrue("LRETURN", Instructions.newInstruction(0xad) instanceof LongReturn);
        assertTrue("FRETURN", Instructions.newInstruction(0xae) instanceof FloatReturn);
        assertTrue("DRETURN", Instructions.newInstruction(0xaf) instanceof DoubleReturn);
        assertTrue("ARETURN", Instructions.newInstruction(0xb0) instanceof RefReturn);
        assertTrue("RETURN", Instructions.newInstruction(0xb1) instanceof Return);
    }

    // ==================== 引用指令测试 ====================

    @Test
    public void testNew() {
        Instruction inst = Instructions.newInstruction(0xbb);
        assertTrue("NEW 应该是 New 类", inst instanceof New);
    }

    @Test
    public void testNewArray() {
        Instruction inst = Instructions.newInstruction(0xbc);
        assertTrue("NEWARRAY 应该是 NewArray 类型", inst instanceof NewArray);
    }

    @Test
    public void testANewArray() {
        Instruction inst = Instructions.newInstruction(0xbd);
        assertTrue("ANEWARRAY 应该是 RefNewArray 类型", inst instanceof RefNewArray);
    }

    @Test
    public void testArrayLength() {
        Instruction inst = Instructions.newInstruction(0xbe);
        assertTrue("ARRAYLENGTH 应该是 ArrayLength 类型", inst instanceof ArrayLength);
    }

    @Test
    public void testInvokeVirtual() {
        Instruction inst = Instructions.newInstruction(0xb6);
        assertTrue("INVOKEVIRTUAL 应该是 InvokeVirtual 类型", inst instanceof InvokeVirtual);
    }

    @Test
    public void testInvokeSpecial() {
        Instruction inst = Instructions.newInstruction(0xb7);
        assertTrue("INVOKESPECIAL 应该是 InvokeSpecial 类型", inst instanceof InvokeSpecial);
    }

    @Test
    public void testInvokeStatic() {
        Instruction inst = Instructions.newInstruction(0xb8);
        assertTrue("INVOKESTATIC 应该是 InvokeStatic 类型", inst instanceof InvokeStatic);
    }

    @Test
    public void testGetField() {
        Instruction inst = Instructions.newInstruction(0xb4);
        assertTrue("GETFIELD 应该是 GetField 类型", inst instanceof GetField);
    }

    @Test
    public void testPutField() {
        Instruction inst = Instructions.newInstruction(0xb5);
        assertTrue("PUTFIELD 应该是 PutField 类型", inst instanceof PutField);
    }

    @Test
    public void testGetStatic() {
        Instruction inst = Instructions.newInstruction(0xb2);
        assertTrue("GETSTATIC 应该是 GetStatic 类型", inst instanceof GetStatic);
    }

    @Test
    public void testPutStatic() {
        Instruction inst = Instructions.newInstruction(0xb3);
        assertTrue("PUTSTATIC 应该是 PutStatic 类型", inst instanceof PutStatic);
    }

    @Test
    public void testInstanceOf() {
        Instruction inst = Instructions.newInstruction(0xc1);
        assertTrue("INSTANCEOF 应该是 InstanceOf 类型", inst instanceof InstanceOf);
    }

    @Test
    public void testCheckCast() {
        Instruction inst = Instructions.newInstruction(0xc0);
        assertTrue("CHECKCAST 应该是 CheckCast 类型", inst instanceof CheckCast);
    }

    // ==================== 特殊指令测试 ====================

    @Test
    public void testInvokeNative() {
        Instruction inst = Instructions.newInstruction(0xfe);
        assertTrue("INVOKENATIVE 应该是 InvokeNative 类型", inst instanceof InvokeNative);
    }

    // ==================== 加载常量指令测试 ====================

    @Test
    public void testLoadConstant() {
        Instruction inst = Instructions.newInstruction(0x12);
        assertTrue("LDC 应该是 LoadConstant 类型", inst instanceof LoadConstant);
    }

    @Test
    public void testLoadConstantWide() {
        Instruction inst = Instructions.newInstruction(0x13);
        assertTrue("LDC_W 应该是 LoadConstantWide 类型", inst instanceof LoadConstantWide);
    }

    @Test
    public void testLoadConstant2Wide() {
        Instruction inst = Instructions.newInstruction(0x14);
        assertTrue("LDC2_W 应该是 LoadConstant2Wide 类型", inst instanceof LoadConstant2Wide);
    }

    // ==================== 扩展指令测试 ====================

    @Test
    public void testWide() {
        Instruction inst = Instructions.newInstruction(0xc4);
        assertTrue("WIDE 应该是 Wide 类型", inst instanceof Wide);
    }

    @Test
    public void testMultiANewArray() {
        Instruction inst = Instructions.newInstruction(0xc5);
        assertTrue("MULTIANEWARRAY 应该是 MultiArrayNewArray 类型", inst instanceof MultiArrayNewArray);
    }

    // ==================== 条件跳转指令测试 ====================

    @Test
    public void testIfConditions() {
        assertTrue("IFEQ", Instructions.newInstruction(0x99) instanceof IfEqual);
        assertTrue("IFNE", Instructions.newInstruction(0x9a) instanceof IfNotEqual);
        assertTrue("IFLT", Instructions.newInstruction(0x9b) instanceof IfLowThan);
        assertTrue("IFGE", Instructions.newInstruction(0x9c) instanceof IfGreatEqualThan);
        assertTrue("IFGT", Instructions.newInstruction(0x9d) instanceof IfGreatThan);
        assertTrue("IFLE", Instructions.newInstruction(0x9e) instanceof IfLowEqualThan);
    }

    // ==================== 引用比较指令测试 ====================

    @Test
    public void testRefComparison() {
        assertTrue("IF_ACMPEQ", Instructions.newInstruction(0xa5) instanceof IfRefCompareEqual);
        assertTrue("IF_ACMPNE", Instructions.newInstruction(0xa6) instanceof IfRefCompareNotEqual);
    }
}
