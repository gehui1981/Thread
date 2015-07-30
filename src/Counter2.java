import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 计数类2可以返回值
 * 
 * @author 小e
 * 
 *         2010-4-19 下午09:55:12
 */
public class Counter2 implements Callable<String> {
	private int countNum;
	private static int taskCount = 0;// 线程id
	private final int taskId = taskCount++;

	public Counter2(int countNum) {
		this.countNum = countNum;
	}

	public String show() {
		return "Id[" + taskId + "] countNum:" + countNum + "  ";
	}

	@Override
	public String call() throws Exception {
		String result = null;
		while (countNum-- > 0) {
			result = show();
			Thread.yield();
		}
		return result;
	}

	
	public static void main(String args[]) {
		ExecutorService exec = Executors.newCachedThreadPool();  
        List<Future<String>> results = new ArrayList<Future<String>>();  
        for(int i = 0; i < 10 ; i++){  
            results.add(exec.submit(new Counter2(10)));  
        }  
        exec.shutdown();  
        for (Future<String> future:results) {  
            System.out.println(future.isDone());  
            try {  
                System.out.println(future.get());  
            } catch (InterruptedException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (ExecutionException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }finally{  
                exec.shutdown();  
            }  
        }  
	}
}