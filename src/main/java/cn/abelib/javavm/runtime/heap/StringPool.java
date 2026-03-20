package cn.abelib.javavm.runtime.heap;

import com.google.common.collect.Maps;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/7/15 23:25
 */
public class StringPool {
    private static Map<String, JvmObject> stringPool = Maps.newHashMap();

    public static JvmObject getString(ClassLoader loader, String string) {
        if (stringPool.containsKey(string)) {
            return stringPool.get(string);
        }
        JvmObject strRef = null;
        try {
            strRef = loader.loadClass("java/lang/String").newObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        // 直接使用 stringValue 字段存储字符串，避免 Java 版本差异（Java 9+ String.value 是 byte[] 而非 char[]）
        strRef.setStringValue(string);
        stringPool.put(string, strRef);
        return strRef;
    }

    public static String getString(JvmObject jStr) {
        // 优先使用 stringValue 字段
        String strVal = jStr.getStringValue();
        if (strVal != null) {
            return strVal;
        }
        // 回退到从 value 字段读取
        JvmObject charArr = jStr.getRefVar("value", "[C");
        if (charArr == null) {
            // 尝试 byte[] (Java 9+)
            charArr = jStr.getRefVar("value", "[B");
        }
        if (charArr != null) {
            return chars2String(charArr.getChars());
        }
        return "";
    }

    private static String chars2String(List<Integer> ints) {
         char[] chars = new char[ints.size()];
         for (int i = 0; i < ints.size(); i ++) {
             chars[i] = (char)ints.get(i).intValue();
         }
        return new String(chars);
    }
}
