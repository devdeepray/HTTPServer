import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;


public class FileTools {

	public static Byte[] readBytes(String pathstr) throws IOException {
		
		// TODO Auto-generated method stub
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
		
		/*String webRootPath = "C:/webtest";
		String userRoot = "C:/Users";*/
		
		String webRootPath = "/users/webdefault";
		String userRoot = "/home/devdeep/dump";
		
		// Cases: first token begins with ~
		// 		First token doesnt start with ~
		StringTokenizer strtkn = new StringTokenizer(param, "/");
		if(strtkn.countTokens() == 0){
			return webRootPath;
		}
		
		String finalPath = new String();
		String token1 = strtkn.nextToken();
		if(token1.startsWith("~"))
			finalPath = userRoot + "/" + token1.substring(1) + "/public_html";
		else
			finalPath = webRootPath + "/" + token1.substring(1);
		
		while(strtkn.hasMoreTokens())
		{
			finalPath = finalPath + "/" + strtkn.nextToken();
		}
		
		return finalPath.replace("%20", " ");
	}
	
	public static Boolean isDirectory(String fpath) throws IOException{
		File f = new File(fpath);
		if(! f.exists()) throw new IOException();
		return f.isDirectory();
	}

	public static Boolean doesExist(String filePath) {
		
		File f = new File(filePath);
		return f.exists();
	}

	public static boolean isValidCGIFile(String filePath) {
		// TODO Auto-generated method stub
		int lastslind = filePath.lastIndexOf('/');
		String sub = filePath.substring(0, lastslind);
		return FileCache.checkDoesExist(sub+"/cgienab");
	}

}
