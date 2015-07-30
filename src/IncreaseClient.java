import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 自加类
 * 
 * @author 小e
 * 
 *         2010-4-24 下午08:39:19
 */
public class IncreaseClient {
	private int base = 0;

	public IncreaseClient() {
	}

	public int increase() {
		int pre = base;
		++base;
		Thread.currentThread().yield();
		++base;
		return pre;
	}

	public synchronized boolean check() {
		int pre = increase();
		System.out.println("pre:" + pre + "\t" + "increase:" + base);
		return pre - base == -2 ? true : false;
	}

	public static void main(String[] args) {
		IncreaseClient ic = new IncreaseClient();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			exec.execute(new IncreaseChecker(ic));
		}
	}

}