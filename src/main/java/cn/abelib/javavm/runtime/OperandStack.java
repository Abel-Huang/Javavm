package cn.abelib.javavm.runtime;

import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.Stack;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:20
 */
public class OperandStack {
    private int size;
    private Stack<Slot> slots;

    public OperandStack(int maxStack) {
        if(maxStack > 0) {
            slots = new Stack<>();
            size = 0;
        }
    }

    public void pushInt(int val) {
        this.slots.push(new Slot(val));
        this.size++;
    }

    public int popInt() {
        int val = this.slots.pop().getNum();
        this.size--;
        return val;
    }

    public void pushBoolean(boolean val) {
        this.slots.push(new Slot(val ? 1 : 0));
        this.size++;
    }

    public boolean popBoolean() {
        int val = this.slots.pop().getNum();
        this.size--;
        return val == 1;
    }

    public void pushFloat(float val) {
        int intVal = Float.floatToIntBits(val);
        this.slots.push(new Slot(intVal));
        this.size++;
    }

    public float popFloat() {
        int intVal = this.slots.pop().getNum();
        this.size--;
        return Float.intBitsToFloat(intVal);
    }

    public void pushLong(long val) {
        int lowVal = (int)(val & 0x000000FFFFFFFFL);
        this.slots.push(new Slot(lowVal));
        this.size++;
        int highVal = (int)(val >> 32);
        this.slots.push(new Slot(highVal));
        this.size++;
    }

    public long popLong() {
        long highVal = this.slots.pop().getNum();
        this.size--;
        long lowVal = this.slots.pop().getNum();
        this.size--;
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
        this.slots.push(new Slot(val));
        this.size++;
    }

    public JvmObject popRef() {
        Slot slot = this.slots.pop();
        this.size--;
        return slot.getRef();
    }

    public void pushSlot(Slot slot) {
        this.slots.push(slot);
        this.size++;
    }
    public Slot popSlot()  {
        Slot slot = this.slots.pop();
        this.size--;
        return slot;
    }

    public JvmObject getRefFromTop(int n) {
        return this.slots.get(this.size - 1 - n).getRef();
    }

    @Override
    public String toString() {
        return "OperandStack{" +
                "size=" + size +
                ", slots=" + slots +
                '}';
    }

    public void clear() {
        this.size = 0;
        this.slots.clear();
    }
}
