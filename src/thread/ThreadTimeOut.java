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
        // ��.
        int timeout = 2;
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Boolean result = false;
        // �������ύ���̳߳���
        Future<Boolean> future = executor.submit(new MyJob("�������"));
        try {
            // �趨��200�����ʱ�������
            result = future.get(timeout, TimeUnit.MILLISECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            System.out.println("�߳��жϳ���");
            // �ж�ִ�д�������߳�
            future.cancel(true);
        } catch (ExecutionException e) {
            System.out.println("�̷߳������");
            // �ж�ִ�д�������߳�
            future.cancel(true);
        } catch (TimeoutException e) {
            // ��ʱ�쳣
            System.out.println("��ʱ��");
            // �ж�ִ�д�������߳�
            future.cancel(true);
        } finally {
            System.out.println("�̷߳���رա�");
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
                    // ����Ҫ
                    return false;
                }
            }
            System.out.println("����ִ��..........");
            return true;
        }
    }
}
