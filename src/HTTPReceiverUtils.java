import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HTTPReceiverUtils {

	public static HTTPObject receive(BufferedInputStream bis) throws IOException {
		// Receives header and payload from the streams
		String header = new String();
		String tmpline = new String();
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
		
		if(header == "") return null;
		
		System.out.println("Header is " + header);
		System.err.println("Starting header parse");
		HTTPHeader hdrobj = new HTTPHeader(header);
		System.err.println("End header parse");
		System.err.println(hdrobj.attributes.get("content-length"));
		// If hdrobj has content length, then read content as well
		if(hdrobj.attributes.get("content-length") == null)
			return new HTTPObject(hdrobj, null);
		
		
		byte [] bodytmp = new byte[Integer.parseInt(hdrobj.attributes.get("content-length"))];
		bis.read(bodytmp);
		System.err.println("End reading bis");
		return new HTTPObject(hdrobj, IOUtils.b2B(bodytmp));
	}

}
