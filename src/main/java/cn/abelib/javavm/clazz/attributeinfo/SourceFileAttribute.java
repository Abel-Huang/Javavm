package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;
import cn.abelib.javavm.clazz.constantinfo.ConstantPool;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:25
 *  SourceFile是可选定长属性，只会出现在ClassFile结构中，用于指出源文件名
 */
public class SourceFileAttribute implements AttributeInfo {
    private ConstantPool cp;
    private int sourceFileIndex;

    public SourceFileAttribute(ConstantPool cp) {
        this.cp = cp;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.sourceFileIndex = reader.readUInt16();
    }

    public String getFileName(){
        return this.cp.getUtf8(this.sourceFileIndex);
    }
}
