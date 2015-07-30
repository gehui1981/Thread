public class Counter implements Runnable {

	private int countNum;
	private static int taskCount = 0;// Ïß³Ìid
	private final int taskId = taskCount++;

	public Counter(int countNum) {
		this.countNum = countNum;
	}

	public String show() {
		return "Id[" + taskId + "] countNum:" + countNum + "  ";
	}

	@Override
	public void run() {
		while (countNum-- > 0) {
			System.out.print(show());
			Thread.yield();
		}
	}

}