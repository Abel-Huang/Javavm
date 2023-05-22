package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 23:52
 *  标记属性，readInfo不用读取任何属性
 */
public abstract class MarkerAttribute implements AttributeInfo {
    @Override
    public void readInfo(ClassReader reader) {
        // read nothing
    }
}
