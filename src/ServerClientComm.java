
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
import java.util.concurrent.TimeUnit;  
  
/** 
 * ģ��������Ļ�������ԭ������wait �� notifyAll 
 * @author Сe 
 * 
 * 2010-4-26 ����09:51:15 
 */  
class Browser{  
    boolean requestFlag ;  
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    /** 
     * �ô�������������������� 
     * @return 
     * @throws InterruptedException  
     */  
    public synchronized void waitForRequest() throws InterruptedException{  
        while(!requestFlag){  
            wait();  
        }  
    }  
    /** 
     * �ȴ���������Ӧ 
     * @throws InterruptedException  
     */  
    public synchronized void waitForResponse() throws InterruptedException{  
        while(requestFlag){  
            wait();  
        }  
    }  
    public synchronized void request(){  
        System.out.println(getTime() + " �ͻ��˷������󡭡�");  
        requestFlag = true;  
        notifyAll();  
    }  
    public synchronized void response(){  
        System.out.println(getTime() + " �������Ӧ���󡭡�");  
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
                System.out.println("��ʼ��������������");  
                browser.request();  
                TimeUnit.MILLISECONDS.sleep(1000);// ģ�ⷢ�������ʱ������  
                browser.waitForResponse();  
            }  
        } catch (InterruptedException e) {  
            // TODO Auto-generated catch block  
            System.out.println("������");  
        }  
        System.out.println("Requestģ���������");  
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
                browser.waitForRequest();// �ȴ�����  
                TimeUnit.MILLISECONDS.sleep(1000);// ģ�⴦�����ݵ�ʱ������  
                browser.response();  
            }  
        } catch (InterruptedException e) {  
            System.out.println("������");  
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
          
        TimeUnit.SECONDS.sleep(5);//ģ��10��  
        exec.shutdownNow();  
    }  
} 