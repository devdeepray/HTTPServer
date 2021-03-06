/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This is the class whose functions provide the main logic for processing 
 * HTTP requests. Called by the processor runnable from the pipe or the main socket thread.
 * getResponse takes HTTPObject and returns HTTPObject as result.
 */


public class HTTPRequestProcessor 
{
	public static HTTPObject getResponse(HTTPObject hto) 
	{
		try
		{	
			
				// POST or GET or HEAD ( Does very similar stuff )
				String filePath = FileTools.parseUriToPath(hto.header.param);
			if(!FileCache.checkDoesExist(filePath)) 
			{
				return HTTPResponses.notFoundHTTPResponse(hto); // File not found
			}
			else if(FileCache.checkIsDirectory(filePath)) 
			{
				return HTTPResponses.directoryRedirectResponse(hto, filePath); // Directory redirect
			}
			if(HTTP11Helpers.isValidCGIRequest(hto, filePath)) 
			{
				return HTTPResponses.CGIResponse(hto, filePath); // Execute CGI script
			}
			if(HTTP11Helpers.isValidSimpleGETRequest(hto, filePath)) 
			{
				return HTTPResponses.standardGetResponse(hto, filePath); // normal GET response
			}
			if(HTTP11Helpers.isValidHEADRequest(hto, filePath)) //HEAD response
			{
				return HTTPResponses.headResponse(hto, filePath);
			}
			else
			{
				return HTTPResponses.badRequestResponse(hto); // Bad request = unimplemented methods
			}
		}
		catch (Exception e)
		{			
			e.printStackTrace();
		}
		return HTTPResponses.internalErrorResponse(hto); // Unknown internal error page
	}
}
