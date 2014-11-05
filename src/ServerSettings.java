/**
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the class for various server parameters. These are loaded from a file, 
 * or they default to original values. File should be placed in res/serversettings.ss
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ConcurrentSkipListSet;

public class ServerSettings { 
	
	public static int socketTimeout = 10000;
	public static boolean keepalive = true;
	public static int portNum  = 9090;
	
	public static int fileCacheTimeout = 6000000;
	public static boolean fileCacheEnabled = true;
	public static int fileCacheSize = 10;
	
	public static int maxThreadCount = 10;
	public static boolean multiThreaded = false;
	public static boolean pipeEnabled = true;
	public static int maxSessionQueue = 10;
	private static int minSessionThreads = 20;
	private static int maxSessionThreads = 20;
	private static long threadKeepAliveTime = 6000000;
	private static int keepAliveMaxRequests = 10;
	
	public static int debugLevel = 0x0;
	
	public static String supportedEncoding = "gzip";
	public static String cgiConfName = "cgienab";
	
	private static String notFoundFilePath = "res/notfound.html";
	private static String redirFilePath = "res/redir.html";
	private static String internalErrorFilePath = "res/internalerror.html";
	private static String badRequestFilePath = "res/badrequest.html";
	private static ConcurrentSkipListSet<String> CGIExtensions = new ConcurrentSkipListSet<String>(Arrays.asList("py","sh"));
	private static ConcurrentSkipListSet <String> textTypes = new ConcurrentSkipListSet<String>(Arrays.asList("txt", "html", "css", "js", "htm"));

	public static String webRootPath = "/users/webdefault";
	public static String userRootPath = "C:/Users";
	public static String userHTMLFolder = "public_html";
	private static String statsFile = "logFolder/log.txt";
	


	public static void loadServerSettings(String fname) throws IOException // Loads server settings from fname
	{
		BufferedReader br = FileTools.getBufferedReader(fname);
		String tmp = null;
		while((tmp = br.readLine()) != null)
		{
			
			String [] vals = tmp.split(":");
			if(vals.length != 2) 
			{
				System.err.println("Bad line in server config file");
				continue;
			}
			vals[0] = vals[0].trim().toLowerCase();
			
			if(vals[0].equals("socket-timeout"))
				socketTimeout = Integer.parseInt(vals[1]);	
			else if(vals[0] .equals("keep-alive"))
				keepalive = Boolean.parseBoolean(vals[1]);
			else if(vals[0].equals("port-num"))
				portNum = Integer.parseInt(vals[1]);
			
			else if(vals[0].equals("file-cache-timeout"))
				fileCacheTimeout = Integer.parseInt(vals[1]);
			else if(vals[0] .equals("file-cache-enabled"))
				fileCacheEnabled = Boolean.parseBoolean(vals[1]);
			else if(vals[0] .equals("file-cache-size"))
				fileCacheSize = Integer.parseInt(vals[1]);
			
			else if(vals[0].equals("max-thread-count"))
				maxThreadCount = Integer.parseInt(vals[1]);
			else if(vals[0].equals("multi-threaded"))
				multiThreaded = Boolean.parseBoolean(vals[1]);
			else if(vals[0].equals("pipe-enabled"))
				pipeEnabled = Boolean.parseBoolean(vals[1]);
			else if(vals[0].equals("max-session-queue"))
				maxSessionQueue = Integer.parseInt(vals[1]);
			else if(vals[0].equals("min-session-threads"))
				minSessionThreads = Integer.parseInt(vals[1]);
			else if(vals[0] .equals("max-session-threads"))
				maxSessionThreads = Integer.parseInt(vals[1]);
			else if(vals[0].equals("thread-keep-alive-time"))
				threadKeepAliveTime = Integer.parseInt(vals[1]);
			else if(vals[0].equals("keep-alive-max-requests"))
				keepAliveMaxRequests = Integer.parseInt(vals[1]);
			
			else if(vals[0].equals("debug-level"))
				debugLevel = Integer.parseInt(vals[1]);
			
			else if(vals[0].equals("cgi-conf-name"))
				cgiConfName = vals[1];
			
			
			else if(vals[0].equals("not-found-file-path"))
				notFoundFilePath = vals[1];
			else if(vals[0].equals("redir-file-path"))
				redirFilePath = vals[1];
			else if(vals[0].equals("internal-error-file-path"))
				internalErrorFilePath = vals[1];
			else if(vals[0].equals("bad-request-file-path"))
				badRequestFilePath = vals[1];
			
			else if(vals[0].equals("cgi-extensions"))
			{
				for(String x : vals[1].split(" "))
				{	
					CGIExtensions.add(x);
				}
			}
			else if(vals[0].equals("text-types"))
			{
				for(String x : vals[1].split(" "))
				{
					textTypes.add(x);
				}
			}
			else if(vals[0].equals("web-root-path"))
			{
				webRootPath = vals[1];
			}
			else if(vals[0].equals("user-root-path"))
			{
				userRootPath = vals[1];
			}
			else if(vals[0].equals("user-html-folder"))
			{
				userHTMLFolder = vals[1];
			}
		}
	}
	
	public static int getSoTimeout()
	{
		return socketTimeout;
	}


	public static int getPortNumber() 
	{
		return portNum;
	}


	public static int getDebugLevel() 
	{
		return debugLevel;
	}


	public static boolean isKeepAlive() 
	{
		return keepalive;
	}


	public static boolean isPiped() 
	{
		return pipeEnabled;
	}


	public static boolean isMultiThreaded() 
	{
		return multiThreaded;
	}


	public static int getMaxSessionQueue()
	{
		return maxSessionQueue;
	}


	public static int getMinThreads() 
	{
		return minSessionThreads;
	}
	
	public static int getMaxThreads()
	{
		return maxSessionThreads;
	}


	public static long getThreadKeepAliveTime()
	{
		return threadKeepAliveTime;
	}


	public static boolean fileCacheOn() 
	{
		return fileCacheEnabled;
	}

	public static String getNotFoundFile() 
	{
		return notFoundFilePath ;
	}


	public static String getRedirFile() 
	{
		return redirFilePath;
	}


	public static String getInternalErrorFile() 
	{
		return internalErrorFilePath;
	}


	public static String getBadRequestFile() 
	{
		return badRequestFilePath ;
	}


	public static boolean isValidCGIExtension(String extn) 
	{
		return CGIExtensions.contains(extn);
	}
	
	public static boolean isTextType(String extn)
	{
		return textTypes.contains(extn);
	}

	public static String getCgiConfName() 
	{
		return cgiConfName;
	}

	public static String getWebRootPath() 
	{
		return webRootPath;
	}

	public static String getUserHTMLFolder() 
	{
		return userHTMLFolder;
	}

	public static String getUserRootPath() 
	{
		return userRootPath;
	}

	public static int getGenCacheSize() 
	{
		return fileCacheSize;
	}

	public static int getKeepAliveMaxRequests() 
	{
		return keepAliveMaxRequests ;
	}

	public static String getStatsFile() 
	{
		return statsFile ;
	}
}
