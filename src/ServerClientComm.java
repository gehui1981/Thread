
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
  
/** 
 * 模拟服务器的基本工作原理，测试wait 与 notifyAll 
 * @author 小e 
 * 
 * 2010-4-26 下午09:51:15 
 */  
class Browser{  
    boolean requestFlag ;  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    /** 
     * 得带浏览器给服务器发请求 
     * @return 
     * @throws InterruptedException  
     */  
    public synchronized void waitForRequest() throws InterruptedException{  
        while(!requestFlag){  
            wait();  
        }  
    }  
    /** 
     * 等待服务器响应 
     * @throws InterruptedException  
     */  
    public synchronized void waitForResponse() throws InterruptedException{  
        while(requestFlag){  
            wait();  
        }  
    }  
    public synchronized void request(){  
        System.out.println(getTime() + " 客户端发送请求……");  
        requestFlag = true;  
        notifyAll();  
    }  
    public synchronized void response(){  
        System.out.println(getTime() + " 服务端响应请求……");  
        requestFlag = false;  
        notifyAll();  
    }  
    private String getTime(){  
        return sdf.format(new Date());  
    }  
}  
class Request implements Runnable{  
    private Browser browser;  
  
    public Request(Browser browser) {  
        this.browser = browser;  
    }  
  
    @Override  
    public void run() {  
        try {  
            while (!Thread.interrupted()) {  
                System.out.println("开始向服务端请求数据");  
                browser.request();  
                TimeUnit.MILLISECONDS.sleep(1000);// 模拟发送请求的时间消耗  
                browser.waitForResponse();  
            }  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            System.out.println("处理打断");  
        }  
        System.out.println("Request模块任务结束");  
    }  
      
      
}  
class Response implements Runnable{  
    private Browser browser;  
      
    public Response(Browser browser) {  
        this.browser = browser;  
    }  
  
  
    @Override  
    public void run() {  
        try {  
            while (!Thread.interrupted()) {  
                browser.waitForRequest();// 等待请求  
                TimeUnit.MILLISECONDS.sleep(1000);// 模拟处理数据的时间消耗  
                browser.response();  
            }  
        } catch (InterruptedException e) {  
            System.out.println("处理打断");  
        }  
    }  
      
}  
public class ServerClientComm {  
    public static void main(String[] args) throws InterruptedException {  
        Browser browser = new Browser();  
        Request request = new Request(browser);  
        Response response = new Response(browser);  
          
        ExecutorService exec = Executors.newCachedThreadPool();  
          
        exec.execute(request);  
        exec.execute(response);  
          
        TimeUnit.SECONDS.sleep(5);//模拟10秒  
        exec.shutdownNow();  
    }  
} 