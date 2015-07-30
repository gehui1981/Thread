import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟考试，时间为120分钟，学生可以再30分钟后交卷， 当学生都交完了 或 时间到者考试结束
 * 
 * @author 小e
 * 
 *         2010-4-30 下午11:14:25
 */
class Student implements Runnable, Delayed {
	private String name;
	private long submitTime;// 交卷时间
	private long workTime;// 考试时间

	public Student() {
		// TODO Auto-generated constructor stub
	}

	public Student(String name, long submitTime) {
		super();
		this.name = name;
		workTime = submitTime;
		// 都转为转为ns
		this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime,
				TimeUnit.MILLISECONDS) + System.nanoTime();
	}

	@Override
	public void run() {
		System.out.println(name + " 交卷,用时" + workTime / 100 + "分钟");
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
			System.out.println("考试开始……");
			while (!Thread.interrupted()) {
				students.take().run();
			}
			System.out.println("考试结束……");
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
			students.put(new Student("学生" + (i + 1), 3000 + r.nextInt(9000)));
		}
		students.put(new Student.EndExam(12000, exec));// 1200为考试结束时间
		exec.execute(new Teacher(students, exec));

	}

}

//public class Exam {  
//    static final int STUDENT_SIZE = 10;  
//    public static void main(String[] args) {  
//        Random r = new Random();  
//        DelayQueue<Student> students = new DelayQueue<Student>();  
//        ExecutorService exec = Executors.newCachedThreadPool();  
//        /***用来记录最后一个交卷的学生的考试时间，然后就可以结束考试了*/  
//        long shouldEndTime = 0;  
//        for(int i = 0; i < STUDENT_SIZE; i++){  
//            long submitTime = 3000 + r.nextInt(9000);  
//            shouldEndTime = shouldEndTime > submitTime ? shouldEndTime:submitTime;  
//            students.put(new Student("学生" + i, submitTime));  
//        }  
//        /**这一句基本上不用要的，主要是用来确定结束的*/  
//        shouldEndTime = shouldEndTime > 12000 ? 12000:shouldEndTime;  
//        students.put(new Student.EndExam((int) shouldEndTime,exec));//1200为考试结束时间  
//        exec.execute(new Teacher(students));  
//    }