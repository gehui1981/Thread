package thread;

public class TraditionalThreadSynchronized {
    public static void main(String[] args) {
        final Outputter output = new Outputter();
        new Thread() {
            public void run() {
                try {
                    output.output("zhangsan");
                } catch (InterruptedException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    output.output("lisi");
                } catch (InterruptedException e) {

                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }
        }.start();
    }
}

class Outputter {

    public synchronized void output(String name) throws InterruptedException {
        // TODO �߳��������
        for (int i = 0; i < name.length(); i++) {
            System.out.print(name.charAt(i));
            Thread.sleep(10);
        }
    }

//    public void output(String name) throws InterruptedException {
//        // TODO Ϊ�˱�֤��name���������һ��ԭ�Ӳ���������������name��ÿ���ַ�
//        // for (int i = 0; i < name.length(); i++) {
//        // System.out.print(name.charAt(i));
//        // Thread.sleep(10);
//        // }
//
//        synchronized (this) {
//            for (int i = 0; i < name.length(); i++) {
//                System.out.print(name.charAt(i));
//                Thread.sleep(10);
//            }
//        }
//    }
}