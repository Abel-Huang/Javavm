package cn.abelib.javavm.testcase;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 23:28
 */
public class MyObject {

    public static int staticVar;
    public int instanceVar;

    public static void main(String[] args) {
        int x = 32768; // ldc
        MyObject myObj = new MyObject(); // new todo failed
        MyObject.staticVar = 10; // putstatic
        System.err.println(MyObject.staticVar);
        x = MyObject.staticVar; // getstatic
        myObj.instanceVar = x; // putfield
        x = myObj.instanceVar; // getfield
        Object obj = myObj;
        if (obj instanceof MyObject) { // instanceof
            myObj = (MyObject) obj; // checkcast
            System.out.println(myObj.instanceVar);
        }
    }
}