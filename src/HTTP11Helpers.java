/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: These are helper functions to check what kind of request it is, 
 * and whether it sticks to HTTP1.1 norms.
 */

public class HTTP11Helpers 
{
	public static boolean isValidGETRequest(HTTPObject hto)
	{
		// Correct syntax of header general GET
		return hto.header.action.equals(StringConstants.getRequestAction) && hasHost(hto); 
	}
	
	public static boolean isValidCGIRequest(HTTPObject hto, String filePath)
	{
		// Correct syntax of header for POST
		return (hto.header.action.equals(StringConstants.postRequestAction)
				|| hto.header.action.equals(StringConstants.getRequestAction)) &&
				FileCache.checkIsValidCGI(filePath) && hasHost(hto);
	}
	
	public static boolean isValidHEADRequest(HTTPObject hto, String filePath)
	{
		// Correct syntax of header for HEAD
		return hto.header.action.equals(StringConstants.headRequestAction) && hasHost(hto); 
	}
	
	public static boolean hasHost(HTTPObject hto)
	{
		// Has host attribute or not
		return hto.header.attributes.containsKey(StringConstants.host.trim().toLowerCase());
	}

	public static boolean isValidSimpleGETRequest(HTTPObject hto, String filePath) 
	{
		int qmindex = hto.header.param.indexOf('?');
		return isValidGETRequest(hto) && (qmindex == -1);
	}
	
	public static String getFileExtType(String filePath) 
	{
		String extn = FileTools.getExtn(filePath); // Get extn
		if(extn.equals("htm")) // Translate htm to type html
		{
			extn = "html";
		}
		if(ServerSettings.isTextType(extn))
			return StringConstants.textType + "/" + extn;
		else
			return StringConstants.binaryContentType;
	}
	public static String getRedirectUri(String param) 
	{	
		// Get redirect uri if directory specified
		int indexoflast = param.length() - 1;
		if(param.charAt(indexoflast) == '/')
			return param + StringConstants.defaultLanding;
		else
			return param + "/" + StringConstants.defaultLanding;		
	}
}
