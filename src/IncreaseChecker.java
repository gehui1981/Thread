/**
 * 验证多线程下自加的准确性
 * 
 * @author 小e
 * 
 *         2010-4-24 下午08:38:14
 */
public class IncreaseChecker extends Thread {
	private IncreaseClient ic;

	public IncreaseChecker(IncreaseClient ic) {
		this.ic = ic;
	}

	@Override
	public void run() {
		while (ic.check()) {
		}
		System.out.println(Thread.currentThread() + "自增出错");
		System.exit(0);
	}

}