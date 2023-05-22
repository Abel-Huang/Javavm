package cn.abelib.javavm;

/**
 * @author abel.huang
 * @version 1.0
 * @date 2023/5/14 23:20
 */
public class GaussTest {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
