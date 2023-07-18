package cn.abelib.javavm.runtime;

import cn.abelib.javavm.runtime.heap.JvmObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:20
 */
public class LocalVars {
    private List<Slot> localVars;

    public LocalVars(int maxLocals) {
        if (maxLocals > 0) {
            this.localVars = new ArrayList<>(maxLocals);
            for (int i = 0; i < maxLocals; i ++) {
                this.localVars.add(new Slot());
            }
        }
    }

    public void setInt(int index, int val) {
        this.localVars.set(index, new Slot(val));
    }

    public int getInt(int index) {
        return this.localVars.get(index).getNum();
    }

    public void setFloat(int index, float val) {
        int intVal = Float.floatToIntBits(val);
        this.localVars.set(index, new Slot(intVal));
    }

    public float getFloat(int index) {
        int intVal = this.localVars.get(index).getNum();
        return Float.intBitsToFloat(intVal);
    }

    public void setLong(int index, long val) {
        int lowVal = (int)(val & 0x000000FFFFFFFFL);
        this.localVars.set(index, new Slot(lowVal));
        int highVal = (int)(val >> 32);
        this.localVars.set(index + 1, new Slot(highVal));
    }

    public long getLong(int index) {
        long lowVal = this.localVars.get(index).getNum();
        long highVal = this.localVars.get(index + 1).getNum();
        return (lowVal & 0x000000FFFFFFFFL) | ((highVal & 0x000000FFFFFFFFL) << 32);
    }

    public void setDouble(int index, double val) {
        long longVal = Double.doubleToLongBits(val);
        this.setLong(index, longVal);
    }

    public double getDouble(int index) {
        long longVal = this.getLong(index);
        return Double.longBitsToDouble(longVal);
    }

    public void setRef(int index, JvmObject val) {
        this.localVars.set(index, new Slot(val));
    }

    public Slot getSlot(int index) {
        return this.localVars.get(index);
    }

    public void setSlot(int index, Slot val) {
        this.localVars.set(index, val);
    }

    public JvmObject getRef(int index) {
        return this.localVars.get(index).getRef();
    }

    @Override
    public String toString() {
        return "LocalVars{" +
                "localVars=" + localVars +
                '}';
    }
}
