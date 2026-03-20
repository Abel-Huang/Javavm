package cn.abelib.javavm.clazz;

import cn.abelib.javavm.clazz.constantinfo.ConstantPool;
import cn.abelib.javavm.runtime.heap.ClassRef;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;
import java.util.Objects;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/8/15 23:32
 */
public class ExceptionTable {
    private ExceptionHandler[] exceptionTable;

    public ExceptionTable(){}

    public ExceptionTable(ExceptionTableEntry[] entries, RuntimeConstantPool cp) {
        this.exceptionTable = new ExceptionHandler[entries.length];
        for (int i = 0; i < entries.length; i ++) {
            ExceptionTableEntry entry = entries[i];
            exceptionTable[i] = new ExceptionHandler(entry.getStartPc(),
                    entry.getEndPc(),
                    entry.getHandlerPc(),
                    getCatchType(cp, entry.getCatchType()));
        }
    }

    private ClassRef getCatchType(RuntimeConstantPool cp, int index) {
        if (index == 0) {
            return null;
        }
        return cp.getConstant(index).getClassRef();
    }

    public ExceptionHandler findExceptionHandler(Clazz exClazz, int pc) {
        for(ExceptionHandler handler : this.exceptionTable) {
            if (pc >= handler.getStartPc() && pc < handler.getEndPc()) {
                // catch-all
                if (Objects.isNull(handler.getCatchType())) {
                    return handler;
                }
                Clazz catchClazz = null;
                try {
                    catchClazz = handler.getCatchType().resolvedClass();
                } catch (IOException e) {
                    e.printStackTrace();
                    // todo
                    System.err.println("IOException");
                }
                if (Objects.isNull(catchClazz)) {
                    return null;
                }
                if (catchClazz == exClazz || catchClazz.isSubClassOf(exClazz)) {
                    return handler;
                }
            }
        }
        return null;
    }
}
