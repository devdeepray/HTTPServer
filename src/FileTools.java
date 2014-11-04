/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: These are generic file access tools. These check things directly from the hard disk
 * Used by the fileCache. Some functions here refer to things stored in the file cache for efficiency.
 * It also contains functions to parse URI to disk path.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class FileTools 
{
	
	public static Byte[] readBytes(String pathstr) throws IOException 
	{	
		File file = new File(pathstr); // Open file
		byte [] tmp = new byte[(int) file.length()]; // byte array to read
		DataInputStream datinp = new DataInputStream(new FileInputStream(file));
		datinp.readFully(tmp); // Read whole file
		datinp.close(); // Close file stream
		return IOUtils.b2B(tmp); // Convert to Byte and return
	}

	public static String parseUriToPath(String URI) 
	{		
		String param;
		int qmindex = URI.indexOf('?'); // Removing parameters passed in the URL
		if(qmindex == -1)
		{
			param = URI;
		}
		else
		{
			param = URI.substring(0, qmindex); // Removed string after and incl. ?
		}
		StringTokenizer strtkn = new StringTokenizer(param, "/");
		if(strtkn.countTokens() == 0) // Blank URI
		{
			return ServerSettings.getWebRootPath();
		}
		String finalPath = new String();
		String token1 = strtkn.nextToken();
		if(token1.startsWith("~")) // User home
		{
			finalPath = ServerSettings.getUserRootPath() + "/" +
							token1.substring(1) + "/" + 
							ServerSettings.getUserHTMLFolder();
		}
		else // Root based URI
		{
			finalPath = ServerSettings.getWebRootPath() + "/" + token1.substring(1);
		}
		while(strtkn.hasMoreTokens()) // Generate complete path
		{
			finalPath = finalPath + "/" + strtkn.nextToken();
		}
		return finalPath.replace("%20", " "); // Replace %20 with spaces and return
	}
	
	public static Boolean isDirectory(String fpath) 
	{	
		File f = new File(fpath);
		return f.isDirectory(); // Return whether path points to directory
	}

	public static Boolean doesExist(String filePath) 
	{
		File f = new File(filePath);
		return f.exists(); // Return if file exists or not
	}

	public static String getExtn(String filename)
	{
		int index = filename.lastIndexOf('.'); // Search for extn split point
		if(index == -1) return ""; // No extension
		else return filename.substring(index+1).toLowerCase(); // Return extension
	}
	
	public static boolean isExecutable(String filename)
	{
		File f = new File(filename);
		return f.canExecute();
	}
	
	public static boolean isReadable(String filename)
	{
		File f = new File(filename);
		return f.canRead();
	}
	
	public static boolean isValidCGIFile(String filePath) 
	{
		int lastSlash = filePath.lastIndexOf('/'); // Index of last slash
		String sub = filePath.substring(0, lastSlash); // get directory
		String fname = filePath.substring(lastSlash + 1);  // get file name
		return FileCache.checkDoesExist(sub+ "/" + ServerSettings.getCgiConfName()) // Check if CGI enabled
				&& ServerSettings.isValidCGIExtension(getExtn(fname)) // Check if valid CGI
				&& isExecutable(filePath); // Check if file execute permission is set
	}

	public static BufferedReader getBufferedReader(String fname) throws FileNotFoundException 
	{
		BufferedReader br = new BufferedReader(new FileReader(fname));
		return br;
	}

}
