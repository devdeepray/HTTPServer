import java.util.HashSet;
import java.lang.Math.*;

public class ThreadManager {
	public static HashSet<HTTPSession> activeThreads = new HashSet<HTTPSession>();
	public static HashSet<HTTPSession> deadThreads = new HashSet<HTTPSession>();
	
	
	public static int getAvailableCount(){
		return java.lang.Math.max(10 - activeThreads.size(), deadThreads.size());
		
	}
	public static HTTPSession getAvailableThread()
	{
		
	}
}
