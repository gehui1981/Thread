package thread;

public class ThreadNotSafe {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                Count count = new Count();
                count.count();
            }
        };
        for (int i = 0; i < 100; i++) {
            new Thread(runnable).start();
        }
    }
}

class Count {
    public void count() {
        int num = 0;
        for (int i = 1; i <= 10; i++) {
            num += i;
        }
        System.out.println(Thread.currentThread().getName() + "-" + num);
    }
}