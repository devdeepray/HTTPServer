import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ThreadPool {
	
	public static ArrayBlockingQueue<Runnable> sessionQueue = new ArrayBlockingQueue<Runnable>(ServerSettings.getMaxSessionQueue());
	public static ExecutorService threadExecutor = new ThreadPoolExecutor(ServerSettings.getMinThreads(), 
												ServerSettings.getMaxThreads(), ServerSettings.getThreadKeepAliveTime(), 
												TimeUnit.MILLISECONDS, sessionQueue);
	
	
	
	public static void executeSessionThread(Runnable runnable){
		threadExecutor.execute(runnable);
	}
	
	
}
