package cn.abelib.javavm.runtime;

import cn.abelib.javavm.runtime.heap.JvmObject;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 19:58
 */
public class Slot {
    private int num;
    private JvmObject ref;

    public Slot(int num) {
        this.num = num;
    }

    public Slot(JvmObject ref) {
        this.ref = ref;
    }

    public Slot() {}

    public int getNum() {
        return num;
    }

    public JvmObject getRef() {
        return ref;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setRef(JvmObject ref) {
        this.ref = ref;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "num=" + num +
                ", ref=" + ref +
                '}';
    }
}
