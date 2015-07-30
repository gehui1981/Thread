import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 测试后台线程
 * 
 * @author 小e
 * 
 *         2010-4-19 下午10:32:05
 */
class client implements Runnable {

	@Override
	public void run() {

	}

}

public class DaemonSever implements Runnable {
	private Thread[] ts = new Thread[10];
	private boolean clientInitFlag = false;

	@Override
	public void run() {
		while (true) {
			try {
				if (!clientInitFlag) {
					clientInitFlag = true;
					for (int i = 0; i < 10; i++) {
						ts[i] = new Thread(new client());
					}
				} else {
					for (int i = 0; i < 10; i++) {
						System.out.print("ts[" + i + "]:" + ts[i].isDaemon()
								+ " ");
					}
					System.out.println();
				}
				System.out.println("server working…………");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {
//		ExecutorService exec = Executors.newCachedThreadPool();
//		Thread server = new Thread(new DaemonSever());
//		server.setDaemon(true);
//		server.start();
		
	    ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {  
	          
	        @Override  
	        public Thread newThread(Runnable r) {  
	            Thread t = new Thread(r);  
	            t.setDaemon(true);  
	            return t;  
	        }  
	    });  
	    Thread server = new Thread(new DaemonSever());  
	    server.setDaemon(true);  
	    //server.start();  
	    exec.execute(server);  
		
		
	}

}