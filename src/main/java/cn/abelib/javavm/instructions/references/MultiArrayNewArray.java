package cn.abelib.javavm.instructions.references;

import cn.abelib.javavm.instructions.base.BytecodeReader;
import cn.abelib.javavm.instructions.base.Instruction;
import cn.abelib.javavm.runtime.Frame;
import cn.abelib.javavm.runtime.OperandStack;
import cn.abelib.javavm.runtime.heap.ClassRef;
import cn.abelib.javavm.runtime.heap.Clazz;
import cn.abelib.javavm.runtime.heap.JvmObject;
import cn.abelib.javavm.runtime.heap.RuntimeConstantPool;

import java.io.IOException;
import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/10 22:58
 */
public class MultiArrayNewArray implements Instruction {
    private int index;
    private int dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readUInt16();
        this.dimensions = reader.readUInt8();
    }

    @Override
    public void execute(Frame frame) {
        RuntimeConstantPool cp = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = cp.getConstant(this.index).getClassRef();
        Clazz arrClass = null;
        try {
            arrClass = classRef.resolvedClass();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.lang.InstantiationError");
        }
        OperandStack stack = frame.getOperandStack();
        int[] counts = popAndCheckCounts(stack, (this.dimensions));
        JvmObject arr = null;
        try {
            arr = newMultiDimensionalArray(counts, arrClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }
        stack.pushRef(arr);
    }

    private JvmObject newMultiDimensionalArray(int[] counts, Clazz arrClass) throws IOException {
        int count = counts[0];
        JvmObject arr = arrClass.newArray(count);
        if (counts.length > 1) {
            List<JvmObject> refs = arr.getRefs();
            int[] newCounts = new int[counts.length - 1];
            System.arraycopy(counts, 1, newCounts, 0, newCounts.length);
            for(int i = 0; i < refs.size(); i ++) {
                refs.set(i, newMultiDimensionalArray(newCounts, arrClass.getComponentClass()));
            }
        }
        return arr;
    }

    private int[] popAndCheckCounts(OperandStack stack, int dimensions) {
        int[] counts = new int[dimensions];
        for(int i = dimensions - 1; i >= 0; i--)  {
            counts[i] = stack.popInt();
            if (counts[i] < 0) {
                throw new RuntimeException("java.lang.NegativeArraySizeException");
            }
        }
        return counts;
    }
}
