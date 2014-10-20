import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.omg.CORBA.portable.InputStream;


public class HTTPRequestProcessor {

	public static HTTPObject getResponse(HTTPObject hto) throws IOException {
		
		// Get process
		if(hto.header.action.equals("GET") || hto.header.action.equals("HEAD")){
			
			String filePath = FileTools.parseUriToPath(hto.header.param);
			if(!FileCache.checkDoesExist(filePath)){
				HTTPObject resp = new HTTPObject();
				resp.header.version = "HTTP/1.1";
				resp.header.action = "404";
				resp.header.param = "NOT FOUND";
				resp.header.attributes.put("Content-Type", "text/html");
				resp.body = FileCache.getPage("res/notfound.html");
				resp.header.attributes.put("Content-Length", ""+ resp.body.length);
				return resp;
			}
			else if(FileCache.checkIsDirectory(filePath)){
				
				String redirUri = getRedirectUri(hto.header.param);
				HTTPObject resp = new HTTPObject();
				resp.header.version = "HTTP/1.1";
				resp.header.action = "301";
				resp.header.param = "PERMANENTLY MOVED";
				resp.header.attributes.put("Location", hto.header.attributes.get("host") + redirUri);
				resp.header.attributes.put("Content-Type", "text/html");
				if(hto.header.action.equals("GET")){
					
					resp.body = FileCache.getPage("res/redir.html");
					resp.header.attributes.put("Content-Length", ""+resp.body.length);
				}
				
				return resp;
			}
			else{
				
				String ftype = getFileExtType(filePath);
				HTTPObject resp = new HTTPObject();
				resp.header.version = "HTTP/1.1";
				resp.header.action = "200";
				resp.header.param = "OK";
				resp.header.attributes.put("Location", hto.header.attributes.get("host") + hto.header.param);
				resp.header.attributes.put("Content-Type", ftype);
				resp.header.attributes.put("Connection : ", "keep-alive");
				resp.body = FileCache.getPage(filePath);
				resp.header.attributes.put("Content-Length", ""+resp.body.length);
				return resp;
			}
			
		}
		
		else if(hto.header.action.equals("POST")){
			// doesnt exist, or isnt vaid CGI or it is all fine
			String filePath = FileTools.parseUriToPath(hto.header.param);
			if(FileTools.isValidCGIFile(filePath)){
				Process process = new ProcessBuilder(filePath).start();
				
				BufferedOutputStream bos = new BufferedOutputStream(process.getOutputStream());
				BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
				InputStreamReader ibsr = new InputStreamReader(bis);
				
				if(hto.body != null)
					bos.write(IOUtils.B2b(hto.body));

				String header = new String();
				String tmpline = new String();
				while(true){
					tmpline = IOUtils.readLineFromStreamReader(ibsr);
					if(tmpline == null){
						break;
					}
					if(tmpline.equals(""))
					{
						break;
					}
					header += tmpline + "\n";
				}
				CGIHeader hdrobj = new CGIHeader(header);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int x;
				while((x = bis.read()) != -1){
					baos.write(x);
					
				}
				hdrobj.version = "HTTP/1.1";
				hdrobj.action = "200";
				hdrobj.param = "OK";
				
				// Otherwise read the body
				byte [] bodytmp = baos.toByteArray();

				hdrobj.attributes.put("Content-Length", ""+bodytmp.length);
				return new HTTPObject(hdrobj, IOUtils.b2B(bodytmp));
				
			}
			
		}
		
		return null;
	}
	
	

	private static String getFileExtType(String filePath) {
		
		StringTokenizer strtkn = new StringTokenizer(filePath, "/");
		String last = null;
		while(strtkn.hasMoreTokens()){
			
			last = strtkn.nextToken();
		}
		
		HashSet <String> textTypes = new HashSet(Arrays.asList("txt", "html", "css", "js", "htm"));
		System.err.println(last);
		String extn = last.split("\\.")[1];
		if(extn.equals("htm")) extn = "html";
		if(textTypes.contains(extn))
			return "text/"+extn;
		else
			return "application/octet-stream";
	}

	private static String getRedirectUri(String param) {
		
		// TODO Auto-generated method stub
		int indexoflast = param.length() - 1;
		if(param.charAt(indexoflast) == '/')
			return param + "index.html";
		else
			return param + "/index.html";		
	}

}
