//(&,|)它们是逻辑操作，(&&,||)它们是条件操作。
//
//&是位运算符，表示按位与运算，&&是逻辑运算符，表示逻辑运算与（and）.
//
//|| 按位或运算符 ，表示按位或运算，||是逻辑或算符，表示逻辑或运算。
//
//条件操作只能操作布尔型的,而逻辑操作不仅可以操作布尔型,而且可以操作数值型的。
//不同点：
//对于A&B，不管a是否为假，仍然要判断B。
//对于A|B，不管a是否为真，仍然要判断B。
//而对于A&&B，A||B在以上情况就不会去判断B了。

public class Test {

    public static void Single() {
        int i = 3;
        if ((i++ > 5) & (i++ < 9)) {
            System.out.println(i);
            System.out.println("恭喜，执行完了条件语句！");
        }
        System.out.println(i);

    }

    public static void Double() {
        int i = 3;
        if ((i++ > 5) && (i++ < 9)) {
            System.out.println(i);
            System.out.println("恭喜，执行完了条件语句！");

        }

        System.out.println(i);
    }

    public static void main(String[] args) {
        Single();
        Double();
        System.out.println(-2 & -1);
        System.out.println(-2 & -1);
    }
}
