import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


public class FileCache {

	static ConcurrentHashMap<String, Byte[]> fileDataMap = new  ConcurrentHashMap<String, Byte[]> ();
	static ConcurrentHashMap<String, Long> timeStamp = new ConcurrentHashMap<String, Long>();
	static ConcurrentHashMap<String, Byte[]> permFileDataMap = new ConcurrentHashMap<String, Byte[]>();
	static ConcurrentHashMap<String, Boolean> isDirectory = new ConcurrentHashMap<String, Boolean>();
	static ConcurrentHashMap<String, Long> isDTimeStamp = new ConcurrentHashMap<String, Long>();
	static ConcurrentHashMap<String, Boolean> doesExist = new ConcurrentHashMap<String, Boolean>();
	static ConcurrentHashMap<String, Long> doesExistTimeStamp = new ConcurrentHashMap<String, Long>();
	
	public static void initPermCache() throws IOException{
		
		// Permanent cache of some files
		permFileDataMap.put(ServerSettings.getRedirFile(), FileTools.readBytes(ServerSettings.getRedirFile()));
		permFileDataMap.put(ServerSettings.getInternalErrorFile(), FileTools.readBytes(ServerSettings.getInternalErrorFile()));
		permFileDataMap.put(ServerSettings.getNotFoundFile(), FileTools.readBytes(ServerSettings.getNotFoundFile()));
		permFileDataMap.put(ServerSettings.getBadRequestFile(), FileTools.readBytes(ServerSettings.getBadRequestFile()));
		
	}
	
	public static Byte[] getPage(String fname) throws IOException{
		
		if(ServerSettings.fileCacheOn()){
			
			// If file cache is on, then get file from cache of possible
			long curtime = System.currentTimeMillis(); // Current system time
			// If file not in cache, or fresh time expired, refresh file
			if((!(fileDataMap.containsKey(fname))) || timeStamp.get(fname) + 600000 < curtime){
				
				fileDataMap.put(fname, FileTools.readBytes(fname)); // Put fresh file in cache
				timeStamp.put(fname, curtime);
			}
			
			return fileDataMap.get(fname);
		}
		
		else{
			
			return FileTools.readBytes(fname); // Read from disk if cache is off
		}
		
	}

	public static boolean checkIsDirectory(String filePath) throws IOException {
		
		// Checks if given file name is a directory or not
		if(ServerSettings.fileCacheOn()){
			
			long curtime = System.currentTimeMillis();
			if(!(isDirectory.containsKey(filePath)) || isDTimeStamp.get(filePath) + 600000 < curtime){
				
				isDirectory.put(filePath, FileTools.isDirectory(filePath));
				isDTimeStamp.put(filePath, curtime);
			}
	
			return isDirectory.get(filePath);
		}
		
		else{
			
			return FileTools.isDirectory(filePath);
		}
		
	}
	
	public static boolean checkDoesExist(String filePath){
		
		// Checks if given file path points to an existing file/directory or not
		if(ServerSettings.fileCacheOn()){
			
			long curtime = System.currentTimeMillis();
			if(!(doesExist.containsKey(filePath)) || doesExistTimeStamp.get(filePath) + 600000 < curtime){
				
				doesExist.put(filePath, FileTools.doesExist(filePath));
				doesExistTimeStamp.put(filePath, curtime);
			}
			
			return doesExist.get(filePath);
		}
		
		else{
			
			return FileTools.doesExist(filePath);
		}
		
	}

	public static Byte[] getPermPage(String filePath) {
		
		// Gets page from permanent cache
		return permFileDataMap.get(filePath);
	}
}
