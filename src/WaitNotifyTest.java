import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WaiteClient implements Runnable {

	@Override
	public void run() {
		try {
			System.out.println("Ω¯»ÎWaitClient");
			synchronized (this) {
				wait();
			}

			System.out.println("hello world!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class NotifyClient implements Runnable {
	private WaiteClient wc;

	public NotifyClient(WaiteClient wc) {
		this.wc = wc;
	}

	@Override
	public void run() {
		try {

			TimeUnit.SECONDS.sleep(3);
			synchronized (wc) {
				wc.notifyAll();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

public class WaitNotifyTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		WaiteClient wc = new WaiteClient();
		NotifyClient nc = new NotifyClient(wc);
		exec.execute(wc);
		exec.execute(nc);
		/*
		 * WaiteClient wc = new WaiteClient(); NotifyClient nc = new
		 * NotifyClient(wc); Thread t1 = new Thread(new WaiteClient()); Thread
		 * t2 = new Thread(new NotifyClient(wc));
		 * 
		 * t1.start(); t2.start();
		 */
		exec.shutdown();
		TimeUnit.SECONDS.sleep(5);
	}

}