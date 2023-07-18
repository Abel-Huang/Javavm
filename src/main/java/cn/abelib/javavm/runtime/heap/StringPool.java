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
        char[] chars = string.toCharArray();
        JvmObject charRef = null;
        JvmObject strRef = null;
        try {
            // init todo setValue
            charRef = new JvmObject(loader.loadClass("[C"), chars.length);
            List<Integer> charList = charRef.getChars();
            for (int i = 0; i < chars.length; i ++) {
                charList.set(i, (int) chars[i]);
            }
            strRef = loader.loadClass("java/lang/String").newObject();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        strRef.setRefVar("value", "[C", charRef);
        stringPool.put(string, strRef);
        return strRef;
    }

    public static String getString(JvmObject jStr) {
        JvmObject charArr = jStr.getRefVar("value", "[C");
        return chars2String(charArr.getChars());
    }

    private static String chars2String(List<Integer> ints) {
         char[] chars = new char[ints.size()];
         for (int i = 0; i < ints.size(); i ++) {
             chars[i] = (char)ints.get(i).intValue();
         }
        return new String(chars);
    }
}
