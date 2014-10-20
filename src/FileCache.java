import java.io.IOException;
import java.util.HashMap;


public class FileCache {

	static HashMap<String, Byte[]> fileDataMap = new  HashMap<String, Byte[]> ();
	static HashMap<String, Long> timeStamp = new HashMap<String, Long>();
	static HashMap<String, Boolean> isDirectory = new HashMap<String, Boolean>();
	static HashMap<String, Long> isDTimeStamp = new HashMap<String, Long>();
	static HashMap<String, Boolean> doesExist = new HashMap<String, Boolean>();
	static HashMap<String, Long> doesExistTimeStamp = new HashMap<String, Long>();
 	
	public static void loadResponsePages() throws IOException {
		// Load redir, internal error and notfound
		fileDataMap.put("redir.html", FileTools.readBytes("res/redir.html"));
		timeStamp.put("redir.html", System.currentTimeMillis());
		fileDataMap.put("internalerror.html", FileTools.readBytes("res/redir.html"));
		timeStamp.put("internalerror.html", System.currentTimeMillis());
		fileDataMap.put("notfound.html", FileTools.readBytes("res/redir.html"));
		timeStamp.put("notfound.html", System.currentTimeMillis());
	}
	
	public static Byte[] getPage(String fname) throws IOException{
		// Gets response page from cache if it does not exist
		long curtime = System.currentTimeMillis();
		// If file not in cache, or fresh time expired, refresh file
		if((!(fileDataMap.containsKey(fname))) || timeStamp.get(fname) + 600000 < curtime){
			
			fileDataMap.put(fname, FileTools.readBytes(fname));
			timeStamp.put(fname, curtime);
		}
		return fileDataMap.get(fname);
	}

	public static boolean checkIsDirectory(String filePath) throws IOException {
		
		// TODO Auto-generated method stub
		long curtime = System.currentTimeMillis();
		if(!(isDirectory.containsKey(filePath)) || isDTimeStamp.get(filePath) + 600000 < curtime){
			
			isDirectory.put(filePath, FileTools.isDirectory(filePath));
			isDTimeStamp.put(filePath, curtime);
		}

		return isDirectory.get(filePath);
	}
	
	public static boolean checkDoesExist(String filePath){
		long curtime = System.currentTimeMillis();
		if(!(doesExist.containsKey(filePath)) || doesExistTimeStamp.get(filePath) + 600000 < curtime){
			
			doesExist.put(filePath, FileTools.doesExist(filePath));
			doesExistTimeStamp.put(filePath, curtime);
		}
		return doesExist.get(filePath);
	}

}
