/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description:This is the class which executes runnables in separate threads. It is used to 
 * execute session threads and the pipe threads. Size of queue and number of threads
 * are specified in ServerSettings.
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;



public class ThreadPool 
{
	public static ExecutorService threadExecutor = new ThreadPoolExecutor // Create Thread pool			 
															(ServerSettings.getMinThreads(), 
															ServerSettings.getMaxThreads(), 
															ServerSettings.getThreadKeepAliveTime(), 
															TimeUnit.MILLISECONDS,
															new ArrayBlockingQueue<Runnable>(ServerSettings.getMaxSessionQueue()));
	
	public static void executeSessionThread(Runnable runnable)
	{
		threadExecutor.execute(runnable); // Put above runnable in queue
	}
}
