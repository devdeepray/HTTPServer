import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentSkipListSet;


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
	private static String redirFilePath = "res/redir.html";
	private static String internalErrorFilePath = "res/internalerror.html";
	private static String badRequestFilePath = "res/badrequest.html";
	private static ConcurrentSkipListSet<String> CGIExtensions = new ConcurrentSkipListSet<String>(Arrays.asList("py","sh"));
	private static ConcurrentSkipListSet <String> textTypes = new ConcurrentSkipListSet<String>(Arrays.asList("txt", "html", "css", "js", "htm"));


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
		return redirFilePath;
	}


	public static String getInternalErrorFile() {
		// TODO Auto-generated method stub
		return internalErrorFilePath;
	}


	public static String getBadRequestFile() {
		// TODO Auto-generated method stub
		return badRequestFilePath ;
	}


	public static boolean isValidCGIExtension(String extn) {
		// TODO Auto-generated method stub
		return CGIExtensions.contains(extn);
	}
	
	public static boolean isTextType(String extn)
	{
		return textTypes.contains(extn);
	}


}
