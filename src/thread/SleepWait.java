package thread;

//sleep��������
//sleep()ʹ��ǰ�߳̽���ͣ��״̬��������ǰ�̣߳����ó�CUP��ʹ�á�Ŀ���ǲ��õ�ǰ�̶߳��԰�ռ�ý��������CPU��Դ������һ��ʱ��������߳�ִ�еĻ���;
//sleep()��Thread���Static(��̬)�ķ�������������ܸı����Ļ��������Ե���һ��Synchronized���е���Sleep()�����ǣ��߳���Ȼ�����ˣ����Ƕ���Ļ�����ľ�б��ͷţ������߳��޷�����������󣨼�ʹ˯��Ҳ���ж���������
//��sleep()����ʱ�������󣬸��̲߳�һ��������ִ�У�������Ϊ�����߳̿����������ж���û�б�����Ϊ����ִ�У����Ǵ��߳̾��и��ߵ����ȼ��� 
//
//wait��������
//wait()������Object����ķ�������һ���߳�ִ�е�wait()����ʱ�����ͽ��뵽һ���͸ö�����صĵȴ����У�ͬʱʧȥ���ͷţ��˶���Ļ�������ʱʧȥ������wait(long timeout)��ʱʱ�䵽����Ҫ�������������������߳̿��Է��ʣ�
//wait()ʹ��notify����notifyAlll����ָ��˯��ʱ�������ѵ�ǰ�ȴ����е��̡߳�
//wiat()�������synchronized block�У��������program runtimeʱ�ӳ���java.lang.IllegalMonitorStateException���쳣��
//
//����sleep()��wait()��������������ǣ�
//sleep()˯��ʱ�����ֶ���������Ȼռ�и�����
//��wait()˯��ʱ���ͷŶ�������
//����wait()��sleep()������ͨ��interrupt()��������̵߳���ͣ״̬���Ӷ�ʹ�߳������׳�InterruptedException����������ʹ�ø÷�������

public class SleepWait implements Runnable {

    int number = 10;

    public void firstMethod() throws Exception {
        synchronized (this) {
            number += 100;
            System.out.println(number);
        }

    }

    public void secondMethod() throws Exception {
        synchronized (this) {
            Thread.sleep(2000);
            // this.wait(2000);
            number *= 200;
        }
    }

    @Override
    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        SleepWait threadTest = new SleepWait();
        Thread thread = new Thread(threadTest);
        thread.start();
        threadTest.secondMethod();
        // System.out.println("threadTest.number " + threadTest.number);
    }
}