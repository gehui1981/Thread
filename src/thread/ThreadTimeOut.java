package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadTimeOut {
    public static void main(String[] args) {
        // 秒.
        int timeout = 2;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Boolean result = false;
        // 将任务提交到线程池中
        Future<Boolean> future = executor.submit(new MyJob("请求参数"));
        try {
            // 设定在200毫秒的时间内完成
            result = future.get(timeout, TimeUnit.MILLISECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println("线程中断出错。");
            // 中断执行此任务的线程
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("线程服务出错。");
            // 中断执行此任务的线程
            future.cancel(true);
        } catch (TimeoutException e) {
            // 超时异常
            System.out.println("超时。");
            // 中断执行此任务的线程
            future.cancel(true);
        } finally {
            System.out.println("线程服务关闭。");
            executor.shutdown();
        }
    }

    static class MyJob implements Callable<Boolean> {
        private String t;

        public MyJob(String temp) {
            this.t = temp;
        }

        public Boolean call() {
            for (int i = 0; i < 999999999; i++) {
                if (i == 999999997) {
                    System.out.println(t);
                }
                if (Thread.interrupted()) {
                    // 很重要
                    return false;
                }
            }
            System.out.println("继续执行..........");
            return true;
        }
    }
}
