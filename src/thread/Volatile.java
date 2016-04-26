/**
 * Project Name: Thread
 * File Name: Volatile.java
 * Package Name: thread
 * Date: 2016年4月26日下午2:16:33
 * Copyright (c) 2016, Yunchuang All Rights Reserved.
 *
 * @author gehge
 *
 * 变更时间 Date:2016年4月26日下午2:16:33  变更原因   变更人员.
 *
 */

package thread;

/**
 * ClassName:Volatile <br/>
 *
 * (TODO:添加处理说明)
 *
 */
public class Volatile {
    public static void main(String args[]) throws InterruptedException {
        for (int i = 1; i <= 100; i++) {
            new Thread() {
                public void run() {
                    Test.one();

                }
            }.start();
        }

        for (int i = 1; i <= 100; i++) {
            new Thread() {
                public void run() {
                    Test.two();

                }
            }.start();
        }
    }
    
}

class Test {
    // static volatile int i = 0, j = 0;
    static int i = 0, j = 0;

    static void one() {
        i++;
        j++;
    }

    static void two() {
        System.out.println("i=" + i + " j=" + j);
    }
}
