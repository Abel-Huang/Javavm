package cn.abelib.javavm.runtime;

import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.Arrays;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:20
 */
public class OperandStack {
    private int size;
    private Slot[] slots;

    public OperandStack(int maxStack) {
        if(maxStack > 0) {
            slots = new Slot[maxStack];
            size = 0;
        }
    }

    public void pushInt(int val) {
        this.slots[this.size] = new Slot(val);
        this.size++;
    }

    public int popInt() {
        this.size--;
        int val = this.slots[this.size].getNum();
        this.slots[this.size] = null;
        return val;
    }

    public void pushFloat(float val) {
        int intVal = Float.floatToIntBits(val);
        this.slots[this.size] = new Slot(intVal);
        this.size++;
    }

    public float popFloat() {
        this.size--;
        int intVal = this.slots[this.size].getNum();
        return Float.intBitsToFloat(intVal);
    }

    public void pushLong(long val) {
        int lowVal = (int)(val & 0x000000FFFFFFFFL);
        this.slots[size] = new Slot(lowVal);
        this.size++;
        int highVal = (int)(val >> 32);
        this.slots[size] = new Slot(highVal);
        this.size++;
    }

    public long popLong() {
        this.size--;
        long highVal = this.slots[this.size].getNum();
        this.size--;
        long lowVal = this.slots[this.size].getNum();
        return (lowVal & 0x000000FFFFFFFFL) | ((highVal & 0x000000FFFFFFFFL) << 32);
    }

    public void pushDouble(double val) {
        long longVal = Double.doubleToLongBits(val);
        this.pushLong(longVal);
    }

    /**
     * @return
     */
    public double popDouble() {
        long longVal = this.popLong();
        return Double.longBitsToDouble(longVal);
    }

    public void pushRef(JvmObject val) {
        this.slots[this.size] = new Slot(val);
        this.size++;
    }

    public JvmObject popRef() {
        this.size--;
        Slot slot = this.slots[this.size];
        // help gc
        this.slots[this.size] = null;
        return slot.getRef();
    }

    public void pushSlot(Slot slot) {
        this.slots[this.size] = slot;
        this.size++;
    }
    public Slot popSlot()  {
        // help gc
        this.slots[this.size] = null;
        this.size--;
        return this.slots[this.size];
    }

    public JvmObject getRefFromTop(int n) {
        return this.slots[this.size - 1 - n].getRef();
    }

    @Override
    public String toString() {
        return "OperandStack{" +
                "size=" + size +
                ", slots=" + Arrays.toString(slots) +
                '}';
    }
}
