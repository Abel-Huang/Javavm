package cn.abelib.javavm.clazz.constantinfo;

import cn.abelib.javavm.clazz.ClassReader;

import java.nio.charset.StandardCharsets;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/22 0:13\
 * Java Class编码MUTF-8（ModifiedUTF-8）
 */
public class ConstantUtf8Info implements ConstantInfo {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void readInfo(ClassReader reader) {
        int length = reader.readUInt16();
        byte[] bytes = reader.readBytes(length);
        this.value = new String(bytes, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
