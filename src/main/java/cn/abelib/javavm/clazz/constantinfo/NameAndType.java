package cn.abelib.javavm.clazz.constantinfo;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/1 0:20
 */
public class NameAndType {
    private String name;
    private String type;

    public NameAndType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
