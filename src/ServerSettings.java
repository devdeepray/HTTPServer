import java.util.Collection;


public class ServerSettings {
	
	public static int socketTimeout = 10000;
	public static boolean keepalive = true;
	public static boolean multiThreaded = false;
	public static int fileCacheTimeout = 6000000;
	public static boolean fileCacheEnabled = true;
	public static int maxThreadCount = 10;
	public static int portNum  = 9090;
	public static int debugLevel = 0xFF;
	public static boolean pipeEnabled = true;
	public static int maxSessionQueue = 10;
	private static int minSessionThreads = 20;
	private static int maxSessionThreads = 20;
	private static long threadKeepAliveTime = 6000000;
	private static String notFoundFilePath = "res/notfound.html";
	


	public static int getSoTimeout() {
		// TODO Auto-generated method stub
		return socketTimeout;
	}


	public static int getPortNumber() {
		// TODO Auto-generated method stub
		return portNum;
	}


	public static int getDebugLevel() {
		// TODO Auto-generated method stub
		return debugLevel;
	}


	public static boolean isKeepAlive() {
		// TODO Auto-generated method stub
		return keepalive;
	}


	public static boolean isPiped() {
		// TODO Auto-generated method stub
		return pipeEnabled;
	}


	public static boolean isMultiThreaded() {
		// TODO Auto-generated method stub
		return multiThreaded;
	}


	public static int getMaxSessionQueue() {
		// TODO Auto-generated method stub
		return maxSessionQueue;
	}


	public static int getMinThreads() {
		// TODO Auto-generated method stub
		return minSessionThreads;
	}
	
	public static int getMaxThreads(){
		
		return maxSessionThreads;
	}


	public static long getThreadKeepAliveTime() {
		// TODO Auto-generated method stub
		return threadKeepAliveTime;
	}


	public static boolean fileCacheOn() {
		// TODO Auto-generated method stub
		return fileCacheEnabled;
	}

	public static String getNotFoundFile() {
		// TODO Auto-generated method stub
		return notFoundFilePath ;
	}


	public static String getRedirFile() {
		// TODO Auto-generated method stub
		return null;
	}


}
