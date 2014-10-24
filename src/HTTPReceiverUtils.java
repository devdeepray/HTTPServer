import java.io.BufferedInputStream;
import java.io.IOException;


public class HTTPReceiverUtils {

	public static HTTPObject receive(BufferedInputStream bis) throws IOException {
		// Receives header and payload from the streams
		String header = new String();
		String tmpline = new String();
		System.err.println("Starting to read header");
		while(true){
			tmpline = IOUtils.readLineFromStreamReader(bis);
			if(tmpline == null){
				break;
			}
			if(tmpline.equals(""))
			{
				break;
			}
			header += tmpline + "\n";
		}
		System.err.println("End reading header");
		
		if(header == "") return null;
		
		System.err.println("Header is " + header);
		System.err.println("Starting to parse header");
		HTTPHeader hdrobj = new HTTPHeader(header);
		System.err.println("End header parse");
		// If hdrobj has content length, then read content as well
		
		if(hdrobj.attributes.get("content-length") == null){
			System.err.println("No body detected.");
			return new HTTPObject(hdrobj, null);
		
		}
		
		System.err.println("Starting to read body");
		byte [] bodytmp = new byte[Integer.parseInt(hdrobj.attributes.get("content-length"))];
		bis.read(bodytmp);
		System.err.println("End reading body");
		return new HTTPObject(hdrobj, IOUtils.b2B(bodytmp));
	}

}
