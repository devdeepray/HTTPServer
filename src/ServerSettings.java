import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	public static String supportedEncoding = "gzip";
	public static String webRootPath = "/users/webdefault";
	public static String userRootPath = "/home/devdeep/dump";
	public static String userHTMLFolder = "public_html";
	public static String cgiConfName = "cgienab";


	public static void loadServerSettings(String fname) throws IOException{
		BufferedReader br = FileTools.getBufferedReader(fname);
		String tmp = null;
		while((tmp = br.readLine()) != null)
		{
			
			String [] vals = tmp.split(":");
			if(vals.length != 2) {
				System.err.println("Bad line in server config file");
				continue;
			}
			if(vals[0].toLowerCase() == "socket-timeout")
				socketTimeout = Integer.parseInt(vals[1]);	
			else if(vals[0].toLowerCase() == "keel-alive")
				keepalive = Boolean.parseBoolean(vals[1]);
			else if(vals[0].toLowerCase() == "multi-threaded")
				multiThreaded = Boolean.parseBoolean(vals[1]);
			else if(vals[0].toLowerCase() == "file-cache-timeout")
				fileCacheTimeout = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "file-cache-enabled")
				fileCacheEnabled = Boolean.parseBoolean(vals[1]);
			else if(vals[0].toLowerCase() == "max-thread-count")
				maxThreadCount = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "port-num")
				portNum = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "debug-level")
				debugLevel = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "pipe-enabled")
				pipeEnabled = Boolean.parseBoolean(vals[1]);
			else if(vals[0].toLowerCase() == "max-session-queue")
				maxSessionQueue = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "min-session-threads")
				minSessionThreads = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "max-session-threads")
				maxSessionThreads = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "thread-keep-alive-time")
				threadKeepAliveTime = Integer.parseInt(vals[1]);
			else if(vals[0].toLowerCase() == "not-found-file-path")
				notFoundFilePath = vals[1];
			else if(vals[0].toLowerCase() == "redir-file-path")
				redirFilePath = vals[1];
			else if(vals[0].toLowerCase() == "internal-error-file-path")
				internalErrorFilePath = vals[1];
			else if(vals[0].toLowerCase() == "bad-request-file-path")
				badRequestFilePath = vals[1];
			else if(vals[0].toLowerCase() == "cgi-extensions"){
				for(String x : vals[1].split(" ")){	
					CGIExtensions.add(x);
				}
			}
			else if(vals[0].toLowerCase() == "text-types"){
				for(String x : vals[1].split(" ")){
					textTypes.add(x);
				}
			}
				
			
		}
		
	}
	
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

	public static String getCgiConfName() {
		// TODO Auto-generated method stub
		return cgiConfName;
	}

	public static String getWebRootPath() {
		// TODO Auto-generated method stub
		return webRootPath;
	}

	public static String getUserHTMLFolder() {
		// TODO Auto-generated method stub
		return userHTMLFolder;
	}

	public static String getUserRootPath() {
		// TODO Auto-generated method stub
		return userRootPath;
	}


}
