/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is an implementation of a file cache. It caches some files
 * and various other properties, like executable or not, directory or not, exists or not
 * so that disk accesses are reduced. Max cache size is limited. Can set value in server settings
 */

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class FileCache 
{
	// HashMap for pages
	static ConcurrentHashMap<String, Byte[]> fileDataMap = new  ConcurrentHashMap<String, Byte[]> ();
	static ConcurrentHashMap<String, Long> fileDataTimeStamp = new ConcurrentHashMap<String, Long>();
	
	// HashMap for permanent cache
	static ConcurrentHashMap<String, Byte[]> permFileDataMap = new ConcurrentHashMap<String, Byte[]>();
	
	// HashMap for whether path is a directory or not
	static ConcurrentHashMap<String, Boolean> isDirectory = new ConcurrentHashMap<String, Boolean>();
	static ConcurrentHashMap<String, Long> isDTimeStamp = new ConcurrentHashMap<String, Long>();
	
	// HashMap for whether a file exists or not
	static ConcurrentHashMap<String, Boolean> doesExist = new ConcurrentHashMap<String, Boolean>();
	static ConcurrentHashMap<String, Long> doesExistTimeStamp = new ConcurrentHashMap<String, Long>();
	
	// HashMap for whether a file is a CGIScript or not
	static ConcurrentHashMap<String, Boolean> isCGIScript = new ConcurrentHashMap<String, Boolean>(); 
	static ConcurrentHashMap<String, Long> isCGIScriptTimeStamp = new ConcurrentHashMap<String, Long>();
	
	public static void initPermCache() throws IOException
	{
		// Permanent cache of some files
		permFileDataMap.put(ServerSettings.getRedirFile(),
								FileTools.readBytes(ServerSettings.getRedirFile()));
		permFileDataMap.put(ServerSettings.getInternalErrorFile(),
								FileTools.readBytes(ServerSettings.getInternalErrorFile()));
		permFileDataMap.put(ServerSettings.getNotFoundFile(),
								FileTools.readBytes(ServerSettings.getNotFoundFile()));
		permFileDataMap.put(ServerSettings.getBadRequestFile(),
								FileTools.readBytes(ServerSettings.getBadRequestFile()));
	}
	
	public static Byte[] getPage(String fname) throws IOException
	{	
		if (ServerSettings.fileCacheOn()) // If caching is on
		{
			long curtime = System.currentTimeMillis(); // Current time
			if (!(fileDataMap.containsKey(fname))
					|| fileDataTimeStamp.get(fname) + 600000 < curtime) // Not fresh
			{
				if (fileDataMap.size() > ServerSettings.getGenCacheSize()) // Full cache
				{
					fileDataMap.clear(); // Dump cache for rebuilding
					fileDataTimeStamp.clear();
				}
				fileDataMap.put(fname, FileTools.readBytes(fname)); // Put fresh file in cache
				fileDataTimeStamp.put(fname, curtime); // Put time stamp 		
			}
			return fileDataMap.get(fname);
		}
		else
		{
			return FileTools.readBytes(fname); // Read from disk if cache is off
		}	
	}
	
	
	// All functions below follow same pattern as above
	public static boolean checkIsDirectory(String filePath) throws IOException
	{	
		if (ServerSettings.fileCacheOn())
		{
			long curtime = System.currentTimeMillis();
			if (!(isDirectory.containsKey(filePath)) 
					|| isDTimeStamp.get(filePath) + 600000 < curtime)
			{
				if (isDirectory.size() > ServerSettings.getGenCacheSize()) // Full cache
				{
					isDirectory.clear(); // Dump cache for rebuilding
					isDTimeStamp.clear();
				}
				isDirectory.put(filePath, FileTools.isDirectory(filePath));
				isDTimeStamp.put(filePath, curtime);
			}
			return isDirectory.get(filePath);
		}
		else
		{
			return FileTools.isDirectory(filePath);
		}
	}
	
	public static boolean checkDoesExist(String filePath)
	{		
		if (ServerSettings.fileCacheOn())
		{
			long curtime = System.currentTimeMillis();
			if (!(doesExist.containsKey(filePath)) 
					|| doesExistTimeStamp.get(filePath) + 600000 < curtime)
			{
				if (doesExist.size() > ServerSettings.getGenCacheSize()) // Full cache
				{
					doesExist.clear(); // Dump cache for rebuilding
					doesExistTimeStamp.clear();
				}
				doesExist.put(filePath, FileTools.doesExist(filePath));
				doesExistTimeStamp.put(filePath, curtime);
			}
			return doesExist.get(filePath);
		}		
		else
		{
			return FileTools.doesExist(filePath);
		}
	}
	
	public static boolean checkIsValidCGI(String filePath)
	{		
		if (ServerSettings.fileCacheOn())
		{
			long curtime = System.currentTimeMillis();
			if (!(isCGIScript.containsKey(filePath)) 
					|| isCGIScriptTimeStamp.get(filePath) + 600000 < curtime)
			{
				if (isCGIScript.size() > ServerSettings.getGenCacheSize()) // Full cache
				{
					isCGIScript.clear(); // Dump cache for rebuilding
					isCGIScriptTimeStamp.clear();
				}
				isCGIScript.put(filePath, FileTools.isValidCGIFile(filePath));
				isCGIScriptTimeStamp.put(filePath, curtime);
			}
			return isCGIScript.get(filePath);
		}		
		else
		{
			return FileTools.isValidCGIFile(filePath);
		}
	}

	public static Byte[] getPermPage(String filePath) 
	{
		return permFileDataMap.get(filePath); // Perm cache page
	}
}
