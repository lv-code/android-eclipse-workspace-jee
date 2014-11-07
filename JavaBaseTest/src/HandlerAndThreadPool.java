import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 练习 线程操作
// http://www.trinea.cn/android/java-android-thread-pool/
public class HandlerAndThreadPool {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
	}
	
	//可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	private static void test1() {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
		    final int index = i;
		    try {
		        Thread.sleep(index * 1000);
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		 
		    cachedThreadPool.execute(new Runnable() {
		 
		        @Override
		        public void run() {
		            System.out.println(index);
		        }
		    });
		}
	}
	
	// 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	private static void test2() {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		for (int i = 0; i < 10; i++) {
		    final int index = i;
		    fixedThreadPool.execute(new Runnable() {
		 
		        @Override
		        public void run() {
		            try {
		                System.out.println(index);
		                Thread.sleep(2000);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    });
		}
	}
	
	// 创建一个定长线程池，支持定时及周期性任务执行。
	private static void test3() {
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
		scheduledThreadPool.schedule(new Runnable() {
		 
		    @Override
		    public void run() {
		        System.out.println("delay 3 seconds");
		    }
		}, 3, TimeUnit.SECONDS);// 表示延迟3秒执行
		
		// 定期执行，表示延迟1秒后每3秒执行一次。ScheduledExecutorService比Timer更安全，功能更强大。
		scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
			 
		    @Override
		    public void run() {
		        System.out.println("delay 1 seconds, and excute every 3 seconds");
		    }
		}, 1, 3, TimeUnit.SECONDS);
	}
	
	// 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
	private static void test4() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		for (int i = 0; i < 10; i++) {
		    final int index = i;
		    singleThreadExecutor.execute(new Runnable() {
		 
		        @Override
		        public void run() {
		            try {
		                System.out.println(index);
		                Thread.sleep(2000);
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        }
		    });
		}
	}
}
