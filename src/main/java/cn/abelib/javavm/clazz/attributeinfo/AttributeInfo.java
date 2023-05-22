package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/19 22:40
 */
public interface AttributeInfo {

    void readInfo(ClassReader reader);
}
