import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ������ ������ ģ��
 * 
 * @author Сe
 * 
 *         2010-4-29 ����09:32:43
 */

class Cook implements Runnable {
	private int productId;
	private BlockingQueue<Product> products;
	private boolean rest;

	public Cook(BlockingQueue<Product> products) {
		this.products = products;
	}

	public void product() {
		Product product = new Product(productId++);
		System.out.println("������" + product);
		products.add(product);
	}

	public boolean isRest() {
		return rest;
	}

	public void setRest(boolean rest) {
		this.rest = rest;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (products.size() >= 10) {
						System.out.println("��ʣ���Ʒ����ʦ��Ϣ��������");
						rest = true;
						wait();
					}
				}
				product();// 50ms����һ��
				TimeUnit.MILLISECONDS.sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("ֹͣ����!");
		}
	}
}

class Product {
	private int productId;

	Product(int productId) {
		this.productId = productId;
	}

	public int getProductId() {
		return productId;
	}

	@Override
	public String toString() {
		return "��Ʒ" + productId;
	}
}

class Customer implements Runnable {
	private BlockingQueue<Product> products;
	private String name;
	private Cook cook;

	public Customer(Cook cook, BlockingQueue<Product> products, String name) {
		this.cook = cook;
		this.products = products;
		this.name = name;
	}

	public void consume() {
		try {
			Product product = products.take();
			System.out.println(name + " ����" + product);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				consume();// 500����һ��
				synchronized (cook) {
					if (products.size() == 0 && cook.isRest()) {
						cook.setRest(false);
						cook.notifyAll();
						System.out.println("��治������ʦ��ʼ��������");
					}
				}
				TimeUnit.MILLISECONDS.sleep(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("�û�:" + name + "ֹͣ����!");
		}

	}
}

public class RestaurantBlockingQueue {
	private Cook cook;

	public RestaurantBlockingQueue(Cook cook) {
		this.cook = cook;
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("���ݿ���");
		ExecutorService exec = Executors.newCachedThreadPool();
		BlockingQueue<Product> products = new LinkedBlockingQueue<Product>();
		Cook cook = new Cook(products);
		RestaurantBlockingQueue restaurant = new RestaurantBlockingQueue(cook);
		exec.execute(cook);
		for (int i = 0; i < 5; i++) {
			exec.execute(new Customer(cook, products, "�û�" + i));
		}

		TimeUnit.SECONDS.sleep(2);// ģ������
		exec.shutdownNow();
		System.out.println("���ݹ���");
	}

}