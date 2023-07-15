package cn.abelib.javavm.runtime.heap;

import cn.abelib.javavm.runtime.LocalVars;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 18:46
 */
public class JvmObject {
    private Clazz clazz;
    private LocalVars data;
    private List<Integer> ints;
    private List<Long> longs;
    private List<Float> floats;
    private List<Double> doubles;
    private List<JvmObject> refs;
    private int size;

    public JvmObject(Clazz clazz) {
        this.clazz = clazz;
        this.data = new LocalVars(clazz.getInstanceSlotCount());
    }

    public JvmObject(Clazz clazz, int count) {
        this.clazz = clazz;
        this.size = count;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public LocalVars getData() {
        if (this.clazz.isArray()) {
            throw new RuntimeException("Is array object!");
        }
        return data;
    }

    public boolean isInstanceOf(Clazz clazz) {
        try {
            return clazz.isAssignableFrom(this.clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("java.io.IOException");
        }
    }

    private void assertArray() {
        if (!this.clazz.isArray()) {
            throw new RuntimeException("Not array!");
        }
    }

    public List<Integer> getBytes() {
        return getInts();
    }

    public List<Integer> getShorts() {
        return getInts();
    }

    /**
     * @return
     */
    public List<Integer> getInts() {
        assertArray();
        if (CollectionUtils.isEmpty(this.ints)) {
            this.ints = Lists.newArrayList();
            for (int i = 0; i < this.size; i ++) {
                this.ints.add(null);
            }
        }
        return this.ints;
    }

    public List<Long> getLongs() {
        assertArray();
        if (CollectionUtils.isEmpty(this.longs)) {
            this.longs = Lists.newArrayList();
            for (int i = 0; i < this.size; i ++) {
                this.longs.add(null);
            }
        }
        return this.longs;
    }

    public List<Integer> getChars() {
        return getInts();
    }

    public List<Float> getFloats() {
        assertArray();
        if (CollectionUtils.isEmpty(this.floats)) {
            this.floats = Lists.newArrayList();
            for (int i = 0; i < this.size; i ++) {
                this.floats.add(null);
            }
        }
        return this.floats;
    }

    public List<Double> getDoubles() {
        assertArray();
        if (CollectionUtils.isEmpty(this.doubles)) {
            this.doubles = Lists.newArrayList();
            for (int i = 0; i < this.size; i ++) {
                this.doubles.add(null);
            }
        }
        return this.doubles;
    }

    /**
     * @return
     */
    public List<JvmObject> getRefs() {
        assertArray();
        if (CollectionUtils.isEmpty(this.refs)) {
            this.refs = Lists.newArrayList();
            for (int i = 0; i < this.size; i ++) {
                this.refs.add(null);
            }
        }
        return this.refs;
    }

    /**
     * acquire array length
     * @return
     */
    public int getArrayLength() {
        assertArray();
        return this.size;
    }
}
