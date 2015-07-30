import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 生产者 消费者 模拟
 * 
 * @author 小e
 * 
 *         2010-4-29 下午09:32:43
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
		System.out.println("生产了" + product);
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
						System.out.println("有剩余产品，厨师休息…………");
						rest = true;
						wait();
					}
				}
				product();// 50ms生产一个
				TimeUnit.MILLISECONDS.sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("停止生产!");
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
		return "产品" + productId;
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
			System.out.println(name + " 消费" + product);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				consume();// 500消费一个
				synchronized (cook) {
					if (products.size() == 0 && cook.isRest()) {
						cook.setRest(false);
						cook.notifyAll();
						System.out.println("库存不够，厨师开始工作……");
					}
				}
				TimeUnit.MILLISECONDS.sleep(500);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("用户:" + name + "停止消费!");
		}

	}
}

public class RestaurantBlockingQueue {
	private Cook cook;

	public RestaurantBlockingQueue(Cook cook) {
		this.cook = cook;
	}

	public static void main(String[] args) throws InterruptedException {

		System.out.println("饭馆开门");
		ExecutorService exec = Executors.newCachedThreadPool();
		BlockingQueue<Product> products = new LinkedBlockingQueue<Product>();
		Cook cook = new Cook(products);
		RestaurantBlockingQueue restaurant = new RestaurantBlockingQueue(cook);
		exec.execute(cook);
		for (int i = 0; i < 5; i++) {
			exec.execute(new Customer(cook, products, "用户" + i));
		}

		TimeUnit.SECONDS.sleep(2);// 模拟五秒
		exec.shutdownNow();
		System.out.println("饭馆关门");
	}

}