package cn.abelib.javavm.runtime.heap;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/4/21 0:04
 */
public class RuntimeConstantPoolInfo {
    private int intValue;

    private boolean isSetIntValue;

    private long longValue;

    private boolean isSetLongValue;

    private float floatValue;

    private boolean isSetFloatValue;

    private double doubleValue;

    private boolean isSetDoubleValue;

    private String utf8Value;

    private boolean isSetUtf8Value;

    private String stringValue;

    private boolean isSetStringValue;

    private ClassRef classRef;

    private boolean isSetClassRef;

    private FieldRef fieldRef;

    private boolean isSetFieldRef;

    private MethodRef methodRef;

    private boolean isSetMethodRef;

    private InterfaceMethodRef interfaceMethodRef;

    private boolean isSetInterfaceMethodRef;

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
        this.isSetIntValue = true;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
        this.isSetLongValue = true;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
        this.isSetFloatValue = true;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        this.isSetDoubleValue = true;
    }

    public String getUtf8Value() {
        return utf8Value;
    }

    public void setUtf8Value(String utf8Value) {
        this.utf8Value = utf8Value;
        this.isSetUtf8Value = true;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
        this.isSetStringValue = true;
    }

    public ClassRef getClassRef() {
        return classRef;
    }

    public void setClassRef(ClassRef classRef) {
        this.classRef = classRef;
        this.isSetClassRef = true;
    }

    public FieldRef getFieldRef() {
        return fieldRef;
    }

    public void setFieldRef(FieldRef fieldRef) {
        this.fieldRef = fieldRef;
        this.isSetFieldRef = true;
    }

    public MethodRef getMethodRef() {
        return methodRef;
    }

    public void setMethodRef(MethodRef methodRef) {
        this.methodRef = methodRef;
        this.isSetMethodRef = true;
    }

    public InterfaceMethodRef getInterfaceMethodRef() {
        return interfaceMethodRef;
    }

    public void setInterfaceMethodRef(InterfaceMethodRef interfaceMethodRef) {
        this.interfaceMethodRef = interfaceMethodRef;
        this.isSetInterfaceMethodRef = true;
    }

    public boolean isSetIntValue() {
        return isSetIntValue;
    }

    public boolean isSetLongValue() {
        return isSetLongValue;
    }

    public boolean isSetFloatValue() {
        return isSetFloatValue;
    }

    public boolean isSetDoubleValue() {
        return isSetDoubleValue;
    }

    public boolean isSetUtf8Value() {
        return isSetUtf8Value;
    }

    public boolean isSetStringValue() {
        return isSetStringValue;
    }

    public boolean isSetClassRef() {
        return isSetClassRef;
    }

    public boolean isSetFieldRef() {
        return isSetFieldRef;
    }

    public boolean isSetMethodRef() {
        return isSetMethodRef;
    }

    public boolean isSetInterfaceMethodRef() {
        return isSetInterfaceMethodRef;
    }
}
