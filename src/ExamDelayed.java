import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ģ�⿼�ԣ�ʱ��Ϊ120���ӣ�ѧ��������30���Ӻ󽻾� ��ѧ���������� �� ʱ�䵽�߿��Խ���
 * 
 * @author Сe
 * 
 *         2010-4-30 ����11:14:25
 */
class Student implements Runnable, Delayed {
	private String name;
	private long submitTime;// ����ʱ��
	private long workTime;// ����ʱ��

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String name, long submitTime) {
		super();
		this.name = name;
		workTime = submitTime;
		// ��תΪתΪns
		this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime,
				TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	@Override
	public void run() {
		System.out.println(name + " ����,��ʱ" + workTime / 100 + "����");
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(submitTime - System.nanoTime(), unit.NANOSECONDS);
	}

	@Override
	public int compareTo(Delayed o) {
		Student that = (Student) o;
		return submitTime > that.submitTime ? 1
				: (submitTime < that.submitTime ? -1 : 0);
	}

	public static class EndExam extends Student {
		private ExecutorService exec;

		public EndExam(int submitTime, ExecutorService exec) {
			super(null, submitTime);
			this.exec = exec;
		}

		@Override
		public void run() {
			exec.shutdownNow();
		}
	}
	
	

}

class Teacher implements Runnable {
	private DelayQueue<Student> students;
	private ExecutorService exec;

	public Teacher(DelayQueue<Student> students, ExecutorService exec) {
		super();
		this.students = students;
		this.exec = exec;
	}

	@Override
	public void run() {
		try {
			System.out.println("���Կ�ʼ����");
			while (!Thread.interrupted()) {
				students.take().run();
			}
			System.out.println("���Խ�������");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

public class ExamDelayed {
	static final int STUDENT_SIZE = 45;

	public static void main(String[] args) {
		Random r = new Random();
		DelayQueue<Student> students = new DelayQueue<Student>();
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < STUDENT_SIZE; i++) {
			students.put(new Student("ѧ��" + (i + 1), 3000 + r.nextInt(9000)));
		}
		students.put(new Student.EndExam(12000, exec));// 1200Ϊ���Խ���ʱ��
		exec.execute(new Teacher(students, exec));

	}

}

//public class Exam {  
//    static final int STUDENT_SIZE = 10;  
//    public static void main(String[] args) {  
//        Random r = new Random();  
//        DelayQueue<Student> students = new DelayQueue<Student>();  
//        ExecutorService exec = Executors.newCachedThreadPool();  
//        /***������¼���һ�������ѧ���Ŀ���ʱ�䣬Ȼ��Ϳ��Խ���������*/  
//        long shouldEndTime = 0;  
//        for(int i = 0; i < STUDENT_SIZE; i++){  
//            long submitTime = 3000 + r.nextInt(9000);  
//            shouldEndTime = shouldEndTime > submitTime ? shouldEndTime:submitTime;  
//            students.put(new Student("ѧ��" + i, submitTime));  
//        }  
//        /**��һ������ϲ���Ҫ�ģ���Ҫ������ȷ��������*/  
//        shouldEndTime = shouldEndTime > 12000 ? 12000:shouldEndTime;  
//        students.put(new Student.EndExam((int) shouldEndTime,exec));//1200Ϊ���Խ���ʱ��  
//        exec.execute(new Teacher(students));  
//    }