package thread;

//sleep（）方法
//sleep()使当前线程进入停滞状态（阻塞当前线程），让出CUP的使用、目的是不让当前线程独自霸占该进程所获的CPU资源，以留一定时间给其他线程执行的机会;
//sleep()是Thread类的Static(静态)的方法；因此他不能改变对象的机锁，所以当在一个Synchronized块中调用Sleep()方法是，线程虽然休眠了，但是对象的机锁并木有被释放，其他线程无法访问这个对象（即使睡着也持有对象锁）。
//在sleep()休眠时间期满后，该线程不一定会立即执行，这是因为其它线程可能正在运行而且没有被调度为放弃执行，除非此线程具有更高的优先级。 
//
//wait（）方法
//wait()方法是Object类里的方法；当一个线程执行到wait()方法时，它就进入到一个和该对象相关的等待池中，同时失去（释放）了对象的机锁（暂时失去机锁，wait(long timeout)超时时间到后还需要返还对象锁）；其他线程可以访问；
//wait()使用notify或者notifyAlll或者指定睡眠时间来唤醒当前等待池中的线程。
//wiat()必须放在synchronized block中，否则会在program runtime时扔出”java.lang.IllegalMonitorStateException“异常。
//
//所以sleep()和wait()方法的最大区别是：
//sleep()睡眠时，保持对象锁，仍然占有该锁；
//而wait()睡眠时，释放对象锁。
//但是wait()和sleep()都可以通过interrupt()方法打断线程的暂停状态，从而使线程立刻抛出InterruptedException（但不建议使用该方法）。

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