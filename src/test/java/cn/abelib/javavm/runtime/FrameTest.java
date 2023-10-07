package cn.abelib.javavm.runtime;

import org.junit.Test;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 21:25
 */
public class FrameTest {

    @Test
    public void testFrame() {
        Frame frame = new Frame(100, 100);
        testLocalVars(frame.getLocalVars());
        testOperandStack(frame.getOperandStack());
    }

    public void testLocalVars(LocalVars vars) {
        vars.setInt(0, 100);
        vars.setInt(1, -100);
        vars.setLong(2, 2997924580L);
        vars.setLong(4, -2997924580L);
        vars.setFloat(6, 3.1415926F);
        vars.setDouble(7, 2.71828182845);
        vars.setRef(9, null);
        System.err.println(vars.getInt(0));
        System.err.println(vars.getInt(1));
        System.err.println(vars.getLong(2));
        System.err.println(vars.getLong(4));
        System.err.println(vars.getFloat(6));
        System.err.println(vars.getDouble(7));
        System.err.println(vars.getRef(9));
    }

    public void testOperandStack(OperandStack ops) {
        ops.pushInt(100);
        ops.pushInt(-100);
        ops.pushLong(2997924580L);
        ops.pushLong(-2997924580L);
        ops.pushFloat(3.1415926F);
        ops.pushDouble(2.71828182845);
        ops.pushRef(null);
        System.err.println(ops.popRef());
        System.err.println(ops.popDouble());
        System.err.println(ops.popFloat());
        System.err.println(ops.popLong());
        System.err.println(ops.popLong());
        System.err.println(ops.popInt());
        System.err.println(ops.popInt());
    }
}
