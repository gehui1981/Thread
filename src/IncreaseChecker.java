/**
 * ��֤���߳����Լӵ�׼ȷ��
 * 
 * @author Сe
 * 
 *         2010-4-24 ����08:38:14
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
		System.out.println(Thread.currentThread() + "��������");
		System.exit(0);
	}

}