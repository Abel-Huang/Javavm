package cn.abelib.javavm.instructions.base;

import cn.abelib.javavm.instructions.comparisons.*;
import cn.abelib.javavm.instructions.constants.*;
import cn.abelib.javavm.instructions.controls.*;
import cn.abelib.javavm.instructions.conversions.*;
import cn.abelib.javavm.instructions.extended.*;
import cn.abelib.javavm.instructions.loads.*;
import cn.abelib.javavm.instructions.maths.*;
import cn.abelib.javavm.instructions.references.*;
import cn.abelib.javavm.instructions.stacks.*;
import cn.abelib.javavm.instructions.stores.*;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/8 18:16
 */
public class Instructions {
    private static Nop NOP = new Nop();
    private static AConstNull ACONSTNULL = new AConstNull();
    private static IntegerConstM1 ICONST_M1 = new IntegerConstM1();
    private static IntegerConst0  ICONST_0 = new IntegerConst0();
    private static IntegerConst1 ICONST_1 = new IntegerConst1();
    private static IntegerConst2 ICONST_2 = new IntegerConst2();
    private static IntegerConst3 ICONST_3 = new IntegerConst3();
    private static IntegerConst4 ICONST_4 = new IntegerConst4();
    private static IntegerConst5 ICONST_5 = new IntegerConst5();
    private static LongConst0 LCONST_0 = new LongConst0();
    private static LongConst1 LCONST_1 = new LongConst1();
    private static FloatConst0 FCONST_0 = new FloatConst0();
    private static FloatConst1 FCONST_1 = new FloatConst1();
    private static FloatConst2 FCONST_2 = new FloatConst2();
    private static DoubleConst0 DCONST_0 = new DoubleConst0();
    private static DoubleConst1 DCONST_1 = new DoubleConst1();
    private static IntegerLoad0 ILOAD_0 = new IntegerLoad0();
    private static IntegerLoad1 ILOAD_1 = new IntegerLoad1();
    private static IntegerLoad2 ILOAD_2 = new IntegerLoad2();
    private static IntegerLoad3 ILOAD_3 = new IntegerLoad3();
    private static LongLoad0 LLOAD_0 = new LongLoad0();
    private static LongLoad1 LLOAD_1 = new LongLoad1();
    private static LongLoad2 LLOAD_2 = new LongLoad2();
    private static LongLoad3 LLOAD_3 = new LongLoad3();
    private static FloatLoad0 FLOAD_0 = new FloatLoad0();
    private static FloatLoad1 FLOAD_1 = new FloatLoad1();
    private static FloatLoad2 FLOAD_2 = new FloatLoad2();
    private static FloatLoad3 FLOAD_3 = new FloatLoad3();
    private static DoubleLoad0 DLOAD_0 = new DoubleLoad0();
    private static DoubleLoad1 DLOAD_1 = new DoubleLoad1();
    private static DoubleLoad2 DLOAD_2 = new DoubleLoad2();
    private static DoubleLoad3 DLOAD_3 = new DoubleLoad3();
    private static RefLoad0 ALOAD_0 = new RefLoad0();
    private static RefLoad1 ALOAD_1 = new RefLoad1();
    private static RefLoad2 ALOAD_2 = new RefLoad2();
    private static RefLoad3 ALOAD_3 = new RefLoad3();
    //private static iaload = new IALOAD();
    //private static laload = new LALOAD();
    //private static faload = new FALOAD();
    //private static daload = new DALOAD();
    //private static aaload = new AALOAD();
    //private static baload = new BALOAD();
    //private static caload = new CALOAD();
    //private static saload = new SALOAD();
    private static IntegerStore0 ISTORE_0 = new IntegerStore0();
    private static IntegerStore1 ISTORE_1 = new IntegerStore1();
    private static IntegerStore2 ISTORE_2 = new IntegerStore2();
    private static IntegerStore3 ISTORE_3 = new IntegerStore3();
    private static LongStore0 LSTORE_0 = new LongStore0();
    private static LongStore1 LSTORE_1 = new LongStore1();
    private static LongStore2 LSTORE_2 = new LongStore2();
    private static LongStore3 LSTORE_3 = new LongStore3();
    private static FloatStore0 FSTORE_0 = new FloatStore0();
    private static FloatStore1 FSTORE_1 = new FloatStore1();
    private static FloatStore2 FSTORE_2 = new FloatStore2();
    private static FloatStore3 FSTORE_3 = new FloatStore3();
    private static DoubleStore0 DSTORE_0 = new DoubleStore0();
    private static DoubleStore1 DSTORE_1 = new DoubleStore1();
    private static DoubleStore2 DSTORE_2 = new DoubleStore2();
    private static DoubleStore3 DSTORE_3 = new DoubleStore3();
    private static RefStore0 ASTORE_0 = new RefStore0();
    private static RefStore1 ASTORE_1 = new RefStore1();
    private static RefStore2 ASTORE_2 = new RefStore2();
    private static RefStore3 ASTORE_3 = new RefStore3();
    //private static iastore = new IASTORE();
    //private static lastore = new LASTORE();
    //private static fastore = new FASTORE();
    //private static dastore = new DASTORE();
    //private static aastore = new AASTORE();
    //private static bastore = new BASTORE();
    //private static castore = new CASTORE();
    //private static sastore = new SASTORE();
    private static Pop POP = new Pop();
    private static Pop2 POP2 = new Pop2();
    private static Duplicate DUP = new Duplicate();
    private static DuplicateX1 DUP_X1 = new DuplicateX1();
    private static DuplicateX2 DUP_X2 = new DuplicateX2();
    private static Duplicate2 DUP2 = new Duplicate2();
    private static Duplicate2X1 DUP2_X1 = new Duplicate2X1();
    private static Duplicate2X2 DUP2_X2 = new Duplicate2X2();
    private static Swap SWAP = new Swap();
    private static IntegerAdd IADD = new IntegerAdd();
    private static LongAdd LADD = new LongAdd();
    private static FloatAdd FADD = new FloatAdd();
    private static DoubleAdd DADD = new DoubleAdd();
    private static IntegerSub ISUB = new IntegerSub();
    private static LongSub LSUB = new LongSub();
    private static FloatSub FSUB = new FloatSub();
    private static DoubleSub DSUB = new DoubleSub();
    private static IntegerMulti IMUL = new IntegerMulti();
    private static LongMulti LMUL = new LongMulti();
    private static FloatMulti FMUL = new FloatMulti();
    private static DoubleMulti DMUL = new DoubleMulti();
    private static IntegerDivide IDIV = new IntegerDivide();
    private static LongDivide LDIV = new LongDivide();
    private static FloatDivide FDIV = new FloatDivide();
    private static DoubleDivide DDIV = new DoubleDivide();
    private static IntegerRemainder IREM = new IntegerRemainder();
    private static LongRemainder LREM = new LongRemainder();
    private static FloatRemainder FREM = new FloatRemainder();
    private static DoubleRemainder DREM = new DoubleRemainder();
    private static IntegerNegative INEG = new IntegerNegative();
    private static LongNegative LNEG = new LongNegative();
    private static FloatNegative FNEG = new FloatNegative();
    private static DoubleNegative DNEG = new DoubleNegative();
    private static IntegerShiftLeft ISHL = new IntegerShiftLeft();
    private static LongShiftLeft LSHL = new LongShiftLeft();
    private static IntegerShiftRight ISHR = new IntegerShiftRight();
    private static LongShiftRight LSHR = new LongShiftRight();
    private static IntegerShiftLogicalRight IUSHR = new IntegerShiftLogicalRight();
    private static LongShiftLogicalRight LUSHR = new LongShiftLogicalRight();
    private static IntegerAnd IAND = new IntegerAnd();
    private static LongAnd LAND = new LongAnd();
    private static IntegerOr IOR = new IntegerOr();
    private static LongOr LOR = new LongOr();
    private static IntegerXor IXOR = new IntegerXor();
    private static LongXor LXOR = new LongXor();
    private static Integer2Long I2L = new Integer2Long();
    private static Integer2Float I2F = new Integer2Float();
    private static Integer2Double I2D = new Integer2Double();
    private static Long2Integer L2I = new Long2Integer();
    private static Long2Float L2F = new Long2Float();
    private static Long2Double L2D = new Long2Double();
    private static Float2Integer F2I = new Float2Integer();
    private static Float2Long F2L = new Float2Long();
    private static Float2Double F2D = new Float2Double();
    private static Double2Integer D2I = new Double2Integer();
    private static Double2Long D2L = new Double2Long();
    private static Double2Float D2F = new Double2Float();
    private static Integer2Byte I2B = new Integer2Byte();
    private static Integer2Char I2C = new Integer2Char();
    private static Integer2Short I2S = new Integer2Short();
    private static LongCompare LCMP = new LongCompare();
    private static FloatCompareLow FCMPL = new FloatCompareLow();
    private static FloatCompareGreat FCMPG = new FloatCompareGreat();
    private static DoubleCompareLow DCMPL = new DoubleCompareLow();
    private static DoubleCompareGreat DCMPG = new DoubleCompareGreat();
    //private static ireturn = new IRETURN();
    //private static lreturn = new LRETURN();
    //private static freturn = new FRETURN();
    //private static dreturn = new DRETURN();
    //private static areturn = new ARETURN();
    //private static _return = new RETURN();
    //private static arraylength = new ARRAY_LENGTH();
    //private static athrow = new ATHROW();
    //private static monitorenter = new MONITOR_ENTER();
    //private static monitorexit = new MONITOR_EXIT();
    //private static invoke_native = new INVOKE_NATIVE();

    public static Instruction newInstruction(int opcode) {
        switch (opcode)  {
            case 0x00: 
                return NOP;
            case 0x01: 
                return ACONSTNULL;
            case 0x02:
                return ICONST_M1;
            case 0x03:
                return ICONST_0;
            case 0x04:
                return ICONST_1;
            case 0x05:
                return ICONST_2;
            case 0x06:
                return ICONST_3;
            case 0x07:
                return ICONST_4;
            case 0x08:
                return ICONST_5;
            case 0x09:
                return LCONST_0;
            case 0x0a:
                return LCONST_1;
            case 0x0b:
                return FCONST_0;
            case 0x0c:
                return FCONST_1;
            case 0x0d:
                return FCONST_2;
            case 0x0e:
                return DCONST_0;
            case 0x0f:
                return DCONST_1;
            case 0x10:
                return new BIPush();
            case 0x11:
                return new SIPush();
             case 0x12:
             	return new LoadConstant();
             case 0x13:
             	return new LoadConstantWide();
             case 0x14:
             	return new LoadConstant2Wide();
            case 0x15:
                return new IntegerLoad();
            case 0x16:
                return new LongLoad();
            case 0x17:
                return new FloatLoad();
            case 0x18:
                return new DoubleLoad();
            case 0x19:
                return new RefLoad();
            case 0x1a:
                return ILOAD_0;
            case 0x1b:
                return ILOAD_1;
            case 0x1c:
                return ILOAD_2;
            case 0x1d:
                return ILOAD_3;
            case 0x1e:
                return LLOAD_0;
            case 0x1f:
                return LLOAD_1;
            case 0x20:
                return LLOAD_2;
            case 0x21:
                return LLOAD_3;
            case 0x22:
                return FLOAD_0;
            case 0x23:
                return FLOAD_1;
            case 0x24:
                return FLOAD_2;
            case 0x25:
                return FLOAD_3;
            case 0x26:
                return DLOAD_0;
            case 0x27:
                return DLOAD_1;
            case 0x28:
                return DLOAD_2;
            case 0x29:
                return DLOAD_3;
            case 0x2a:
                return ALOAD_0;
            case 0x2b:
                return ALOAD_1;
            case 0x2c:
                return ALOAD_2;
            case 0x2d:
                return ALOAD_3;
            // case 0x2e:
            // 	return iaload
            // case 0x2f:
            // 	return laload
            // case 0x30:
            // 	return faload
            // case 0x31:
            // 	return daload
            // case 0x32:
            // 	return aaload
            // case 0x33:
            // 	return baload
            // case 0x34:
            // 	return caload
            // case 0x35:
            // 	return saload
            case 0x36:
                return new IntegerStore();
            case 0x37:
                return new LongStore();
            case 0x38:
                return new FloatStore();
            case 0x39:
                return new DoubleStore();
            case 0x3a:
                return new RefStore();
            case 0x3b:
                return ISTORE_0;
            case 0x3c:
                return ISTORE_1;
            case 0x3d:
                return ISTORE_2;
            case 0x3e:
                return ISTORE_3;
            case 0x3f:
                return LSTORE_0;
            case 0x40:
                return LSTORE_1;
            case 0x41:
                return LSTORE_2;
            case 0x42:
                return LSTORE_3;
            case 0x43:
                return FSTORE_0;
            case 0x44:
                return FSTORE_1;
            case 0x45:
                return FSTORE_2;
            case 0x46:
                return FSTORE_3;
            case 0x47:
                return DSTORE_0;
            case 0x48:
                return DSTORE_1;
            case 0x49:
                return DSTORE_2;
            case 0x4a:
                return DSTORE_3;
            case 0x4b:
                return ASTORE_0;
            case 0x4c:
                return ASTORE_1;
            case 0x4d:
                return ASTORE_2;
            case 0x4e:
                return ASTORE_3;
            // case 0x4f:
            // 	return iastore
            // case 0x50:
            // 	return lastore
            // case 0x51:
            // 	return fastore
            // case 0x52:
            // 	return dastore
            // case 0x53:
            // 	return aastore
            // case 0x54:
            // 	return bastore
            // case 0x55:
            // 	return castore
            // case 0x56:
            // 	return sastore
            case 0x57:
                return POP;
            case 0x58:
                return POP2;
            case 0x59:
                return DUP;
            case 0x5a:
                return DUP_X1;
            case 0x5b:
                return DUP_X2;
            case 0x5c:
                return DUP2;
            case 0x5d:
                return DUP2_X1;
            case 0x5e:
                return DUP2_X2;
            case 0x5f:
                return SWAP;
            case 0x60:
                return IADD;
            case 0x61:
                return LADD;
            case 0x62:
                return FADD;
            case 0x63:
                return DADD;
            case 0x64:
                return ISUB;
            case 0x65:
                return LSUB;
            case 0x66:
                return FSUB;
            case 0x67:
                return DSUB;
            case 0x68:
                return IMUL;
            case 0x69:
                return LMUL;
            case 0x6a:
                return FMUL;
            case 0x6b:
                return DMUL;
            case 0x6c:
                return IDIV;
            case 0x6d:
                return LDIV;
            case 0x6e:
                return FDIV;
            case 0x6f:
                return DDIV;
            case 0x70:
                return IREM;
            case 0x71:
                return LREM;
            case 0x72:
                return FREM;
            case 0x73:
                return DREM;
            case 0x74:
                return INEG;
            case 0x75:
                return LNEG;
            case 0x76:
                return FNEG;
            case 0x77:
                return DNEG;
            case 0x78:
                return ISHL;
            case 0x79:
                return LSHL;
            case 0x7a:
                return ISHR;
            case 0x7b:
                return LSHR;
            case 0x7c:
                return IUSHR;
            case 0x7d:
                return LUSHR;
            case 0x7e:
                return IAND;
            case 0x7f:
                return LAND;
            case 0x80:
                return IOR;
            case 0x81:
                return LOR;
            case 0x82:
                return IXOR;
            case 0x83:
                return LXOR;
            case 0x84:
                return new IntegerIncrement();
            case 0x85:
                return I2L;
            case 0x86:
                return I2F;
            case 0x87:
                return I2D;
            case 0x88:
                return L2I;
            case 0x89:
                return L2F;
            case 0x8a:
                return L2D;
            case 0x8b:
                return F2I;
            case 0x8c:
                return F2L;
            case 0x8d:
                return F2D;
            case 0x8e:
                return D2I;
            case 0x8f:
                return D2L;
            case 0x90:
                return D2F;
            case 0x91:
                return I2B;
            case 0x92:
                return I2C;
            case 0x93:
                return I2S;
            case 0x94:
                return LCMP;
            case 0x95:
                return FCMPL;
            case 0x96:
                return FCMPG;
            case 0x97:
                return DCMPL;
            case 0x98:
                return DCMPG;
            case 0x99:
                return new IfEqual();
            case 0x9a:
                return new IfNotEqual();
            case 0x9b:
                return new IfLowThan();
            case 0x9c:
                return new IfGreatEqualThan();
            case 0x9d:
                return new IfGreatThan();
            case 0x9e:
                return new IfLowEqualThan();
            case 0x9f:
                return new IfIntegerCompareEqual();
            case 0xa0:
                return new IfIntegerCompareNotEqual();
            case 0xa1:
                return new IfIntegerCompareLowThan();
            case 0xa2:
                return new IfIntegerCompareGreatEqual();
            case 0xa3:
                return new IfIntegerCompareGreatThan();
            case 0xa4:
                return new IfIntegerCompareLowEqual();
            case 0xa5:
                return new IfRefCompareEqual();
            case 0xa6:
                return new IfRefCompareNotEqual();
            case 0xa7:
                return new Goto();
            // case 0xa8:
            // 	return new JSR();
            // case 0xa9:
            // 	return new RET();
            case 0xaa:
                return new TableSwitch();
            case 0xab:
                return new LookupSwitch();
             case 0xac:
             	return new IntegerReturn();
             case 0xad:
             	return new LongReturn();
             case 0xae:
             	return new FloatReturn();
             case 0xaf:
             	return new DoubleReturn();
             case 0xb0:
             	return new RefReturn();
             case 0xb1:
             	return new Return();
            case 0xb2:
                return new GetStatic();
            case 0xb3:
             	return new PutStatic();
             case 0xb4:
             	return new GetField();
             case 0xb5:
             	return new PutField();
            case 0xb6:
            		return new InvokeVirtual();
             case 0xb7:
             	return new InvokeSpecial();
             case 0xb8:
             	return new InvokeStatic();
             case 0xb9:
             	return new InvokeInterface();
            // case 0xba:
            // 	return new INVOKE_DYNAMIC();
             case 0xbb:
             	return new New();
            // case 0xbc:
            // 	return new NEW_ARRAY();
            // case 0xbd:
            // 	return new ANEW_ARRAY();
            // case 0xbe:
            // 	return arraylength
            // case 0xbf:
            // 	return athrow
            // case 0xc0:
            // 	return new CHECK_CAST();
            // case 0xc1:
            // 	return new INSTANCE_OF();
            // case 0xc2:
            // 	return monitorenter
            // case 0xc3:
            // 	return monitorexit
            case 0xc4:
                return new Wide();
            // case 0xc5:
            // 	return new MULTI_ANEW_ARRAY();
            case 0xc6:
                return new IfNull();
            case 0xc7:
                return new IfNonNull();
            case 0xc8:
                return new GotoWide();
            // case 0xc9:
            // 	return new JSR_W();
            // case 0xca: breakpoint
            // case 0xfe: impdep1
            // case 0xff: impdep2
            default:
                throw new RuntimeException(String.format("Unsupported opcode: 0x%x!", opcode));
        }
    }
}
