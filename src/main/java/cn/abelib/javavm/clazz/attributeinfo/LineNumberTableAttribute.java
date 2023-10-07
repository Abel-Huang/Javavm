package cn.abelib.javavm.clazz.attributeinfo;

import cn.abelib.javavm.clazz.ClassReader;
import cn.abelib.javavm.clazz.LineNumberTableEntry;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/3/27 2:25
 * 属性表存放方法的行号信息
 */
public class LineNumberTableAttribute implements AttributeInfo {
    private LineNumberTableEntry[] lineNumberTable;

    @Override
    public void readInfo(ClassReader reader) {
        int lineNumberTableLength = reader.readUInt16();
        this.lineNumberTable = new LineNumberTableEntry[lineNumberTableLength];
        for( int i = 0; i < lineNumberTableLength; i ++) {
            this.lineNumberTable[i] = new LineNumberTableEntry(reader.readUInt16(),
                    reader.readUInt16());
        }
    }

    public int getLineNumber(int pc) {
        for (int i = this.lineNumberTable.length - 1; i >= 0; i--) {
            LineNumberTableEntry entry = this.lineNumberTable[i];
            if (pc >= entry.getStartPc()) {
                return entry.getLineNumber();
            }
        }
        return -1;
    }
}
