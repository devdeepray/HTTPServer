import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class FileTools {
	
	// Class of tools to read from disk, parse URI, etc
	public static Byte[] readBytes(String pathstr) throws IOException {
		
		// Reads bytes from disk from file at pathstr
		File file = new File(pathstr);
		Byte [] binFile = new Byte[(int) file.length()];
		byte [] tmp = new byte[(int) file.length()];
		DataInputStream datinp;
		datinp = new DataInputStream(new FileInputStream(file));
		datinp.readFully(tmp);
		datinp.close();
		int i = 0;
		for(byte x : tmp){
			
			binFile[i++] = x;
		}
		
		return binFile;
	}

	public static String parseUriToPath(String param) {		
		
		// Parses URI to on disk path
		// Cases: first token begins with ~
		// 		First token doesnt start with ~
		StringTokenizer strtkn = new StringTokenizer(param, "/");
		if(strtkn.countTokens() == 0){
			return ServerSettings.getWebRootPath();
		}
		
		String finalPath = new String();
		String token1 = strtkn.nextToken();
		if(token1.startsWith("~"))
			finalPath = ServerSettings.getUserRootPath() + "/" + token1.substring(1) + "/" + ServerSettings.getUserHTMLFolder();
		else
			finalPath = ServerSettings.getWebRootPath() + "/" + token1.substring(1);
		
		while(strtkn.hasMoreTokens())
		{
			finalPath = finalPath + "/" + strtkn.nextToken();
		}
		
		return finalPath.replace("%20", " ");
	}
	
	public static Boolean isDirectory(String fpath) throws IOException{
		
		// Checks if a path points to directory or a file
		File f = new File(fpath);
		if(! f.exists()) throw new IOException();
		return f.isDirectory();
	}

	public static Boolean doesExist(String filePath) {
		
		// CHecks if a file exists on disk
		File f = new File(filePath);
		return f.exists();
	}

	public static String getExtn(String filename){
		
		int index = filename.indexOf('.');
		if(index == -1) return "";
		else return filename.substring(index+1);
	}
	
	public static boolean isValidCGIFile(String filePath) {
		
		// Checks if given file is okay to run as CGI script
		// Assumes file exists and it is not a directory
		int lastslind = filePath.lastIndexOf('/');
		String sub = filePath.substring(0, lastslind);
		String fname = filePath.substring(lastslind + 1);
		return FileCache.checkDoesExist(sub+ "/" + ServerSettings.getCgiConfName()) && ServerSettings.isValidCGIExtension(getExtn(fname));
	}

	public static BufferedReader getBufferedReader(String fname) throws FileNotFoundException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader(fname));
		return br;
	}

}
