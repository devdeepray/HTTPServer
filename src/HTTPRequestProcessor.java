import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;


public class HTTPRequestProcessor {
	
	public static HTTPObject getResponse(HTTPObject hto) {
		try{	
			// Get process
			if( hto.header.action.equals(StringConstants.postRequestAction) || 
				hto.header.action.equals(StringConstants.getRequestAction) || 
				hto.header.action.equals(StringConstants.headRequestAction))	{
				String filePath = FileTools.parseUriToPath(hto.header.param);
				if(!FileCache.checkDoesExist(filePath)) return notFoundHTTPResponse(hto);
				else if(FileCache.checkIsDirectory(filePath)) return directoryRedirectResponse(hto, filePath);
				else if(FileTools.isValidCGIFile(filePath)) return CGIResponse(hto, filePath);
				else return standardGetResponse(hto, filePath);
				
			}
			
			else return badRequestResponse(hto);
		}
		catch (Exception e){
			
			return internalErrorResponse(hto);
		}
	}
	
	

	private static HTTPObject badRequestResponse(HTTPObject hto) {
		HTTPObject resp = new HTTPObject();
		resp.header.version = StringConstants.HTTP11;
		resp.header.action = StringConstants.badRequestResponseCode;
		resp.header.param = StringConstants.badRequestResponseMessage;
		resp.header.attributes.put(StringConstants.connection, StringConstants.closeConnection);
		resp.header.attributes.put(StringConstants.contentType, StringConstants.textHtml);
		resp.body = FileCache.getPermPage(ServerSettings.getBadRequestFile());
		return resp;
	}



	private static HTTPObject standardGetResponse(HTTPObject hto, String filePath) {
		
		String ftype = getFileExtType(filePath);
		HTTPObject resp = new HTTPObject();
		resp.header.version = StringConstants.HTTP11;
		resp.header.action = StringConstants.okResponseCode;
		resp.header.param = StringConstants.okResponseMessage;
		if(ServerSettings.isKeepAlive())
			resp.header.attributes.put(StringConstants.connection, StringConstants.keepAlive);
		else
			resp.header.attributes.put(StringConstants.connection,  StringConstants.closeConnection);
		resp.header.attributes.put(StringConstants.location, hto.header.attributes.get(StringConstants.host) + hto.header.param);
		resp.header.attributes.put(StringConstants.contentType, ftype);
		String enc;
		if((enc = hto.header.attributes.get(StringConstants.acceptEncoding.toLowerCase())) != null){

			HashSet<String> encset = new HashSet<String>(Arrays.asList(enc.split("\\s*,\\s*")));
			if(encset.contains(ServerSettings.supportedEncoding)){
				resp.header.attributes.put(StringConstants.contentEncoding, ServerSettings.supportedEncoding);
				if(ServerSettings.supportedEncoding.equals(StringConstants.gzip))
					try {
						resp.body = IOUtils.compressGzip(FileCache.getPage(filePath));
					} catch (IOException e) {
						return internalErrorResponse(hto);
					}
				else if(ServerSettings.supportedEncoding.equals(StringConstants.deflate))
					try {
						resp.body = IOUtils.compressDeflate(FileCache.getPage(filePath));
					} catch (IOException e) {
						return internalErrorResponse(hto);
					}
				else{
					System.err.println("Unsupported compression algorithm. Code bug or config file bug?");
					return internalErrorResponse(hto);
				}
			}
		}
		
		resp.header.attributes.put(StringConstants.contentLength, ""+(resp.body.length));
		return resp;
	}



	private static HTTPObject CGIResponse(HTTPObject hto, String filePath){
	
		try {
		
			Process process = new ProcessBuilder(filePath).start();
		
			BufferedOutputStream bos = new BufferedOutputStream(process.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(process.getInputStream());
			
			if(hto.body != null && hto.header.action.equals(StringConstants.postRequestAction))
				bos.write(IOUtils.B2b(hto.body));
	
			String header = new String();
			String tmpline = new String();
			while(true){
				
				tmpline = IOUtils.readLineFromStreamReader(bis);
				if(tmpline == null || tmpline.equals("")){
					
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
			
			hdrobj.version = StringConstants.HTTP11;
			hdrobj.action = StringConstants.okResponseCode;
			hdrobj.param = StringConstants.okResponseMessage;
			
			// Otherwise read the body
			byte [] bodytmp = baos.toByteArray();
	
			hdrobj.attributes.put(StringConstants.contentLength, ""+bodytmp.length);
			if(ServerSettings.isKeepAlive())
				hdrobj.attributes.put(StringConstants.connection, StringConstants.keepAlive);
			else
				hdrobj.attributes.put(StringConstants.connection,  StringConstants.closeConnection);
			return (new HTTPObject(hdrobj, IOUtils.b2B(bodytmp)));
		}
		
		catch (Exception e){
			
			e.printStackTrace();
			return internalErrorResponse(hto);
		}			
		
	}

	private static HTTPObject internalErrorResponse(HTTPObject hto) {
		
		HTTPObject resp = new HTTPObject();
		resp.header.version = StringConstants.HTTP11;
		resp.header.action = StringConstants.internalErrorCode;
		resp.header.param = StringConstants.internalErrorMessage;
		resp.header.attributes.put(StringConstants.connection, StringConstants.closeConnection);
		resp.header.attributes.put(StringConstants.contentType, StringConstants.textHtml);
		resp.body = FileCache.getPermPage(ServerSettings.getInternalErrorFile());
		return resp;
	}

	private static HTTPObject directoryRedirectResponse(HTTPObject hto, String filePath){
		
		String redirUri = getRedirectUri(hto.header.param);
		HTTPObject resp = new HTTPObject();
		resp.header.version = StringConstants.HTTP11;
		resp.header.action = StringConstants.permanentlyMovedCode;
		resp.header.param = StringConstants.permanentlyMovedMessage;
		if(ServerSettings.isKeepAlive())
			resp.header.attributes.put(StringConstants.connection, StringConstants.keepAlive);
		else
			resp.header.attributes.put(StringConstants.connection,  StringConstants.closeConnection);
		System.out.println(" Location is : http://" + hto.header.attributes.get(StringConstants.host) + redirUri);
		resp.header.attributes.put(StringConstants.location, redirUri);
		resp.header.attributes.put(StringConstants.contentType, StringConstants.textHtml);
		if(hto.header.action.equals(StringConstants.getRequestAction)){
			
			resp.body = FileCache.getPermPage(ServerSettings.getRedirFile());
			resp.header.attributes.put("Content-Length", ""+resp.body.length);
		}
		
		return resp;
	}

	private static HTTPObject notFoundHTTPResponse(HTTPObject hto){
		
		HTTPObject resp = new HTTPObject();
		resp.header.version = StringConstants.HTTP11;
		resp.header.action = StringConstants.notFoundCode;
		resp.header.param = StringConstants.notFoundMessage;
		if(ServerSettings.isKeepAlive())
			resp.header.attributes.put(StringConstants.connection, StringConstants.keepAlive);
		else
			resp.header.attributes.put(StringConstants.connection,  StringConstants.closeConnection);
		resp.header.attributes.put(StringConstants.contentType, StringConstants.textHtml);
		resp.body = FileCache.getPermPage(ServerSettings.getNotFoundFile());
		resp.header.attributes.put(StringConstants.contentLength, ""+ resp.body.length);
		return resp;
	}



	private static String getFileExtType(String filePath) {
		
		StringTokenizer strtkn = new StringTokenizer(filePath, "/");
		String last = null;
		while(strtkn.hasMoreTokens()){
			
			last = strtkn.nextToken();
		}
		
		System.err.println(last);
		String extn = last.split("\\.")[1];
		if(ServerSettings.isTextType(extn))
			return StringConstants.textType + "/" + extn;
		else
			return StringConstants.binaryContentType;
	}

	private static String getRedirectUri(String param) {
		
		System.out.println("Param is " + param);
		// Get redirect uri if directory specified
		int indexoflast = param.length() - 1;
		if(param.charAt(indexoflast) == '/')
			return param + StringConstants.defaultLanding;
		else
			return param + "/" + StringConstants.defaultLanding;		
	}

}
