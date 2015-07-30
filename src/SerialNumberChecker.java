import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ��֤Java�е����������ǵ�����
 * 
 * @author Сe
 * 
 *         2010-4-24 ����09:53:50
 */
public class SerialNumberChecker {
	private static Set<Integer> serialNumberSet = new HashSet<Integer>();
	static int base = 0;
	static int THREAD_SIZE = 10;

	static class SerialChecker implements Runnable {
		private SerialNumberChecker snc;

		public SerialChecker(SerialNumberChecker snc) {
			this.snc = snc;
		}

		@Override
		public void run() {
			while (true) {
				// int number = snc.nextNumber();
				int number = snc.base++;
				if (serialNumberSet.contains(number)) {
					System.out.println("�����ظ�ֵ:" + number);
					System.exit(0);
				} else {
					serialNumberSet.add(number);
				}
			}
		}

	}

	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		SerialNumberChecker snc = new SerialNumberChecker();
		for (int i = 0; i < THREAD_SIZE; i++) {
			exec.execute(new SerialChecker(snc));
		}
	}

}