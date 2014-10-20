import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HTTPReceiver {

	public static HTTPObject receive(BufferedInputStream bis, InputStreamReader ibsr) throws IOException {
		// Receives header and payload from the streams
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
		
		if(header == "") return null;
		
		System.out.println("Header is " + header);
		System.err.println("Starting header parse");
		HTTPHeader hdrobj = new HTTPHeader(header);
		System.err.println("End header parse");
		System.err.println(hdrobj.attributes.get("content-length"));
		// If hdrobj has content length, then read content as well
		if(hdrobj.attributes.get("content-length") == null)
			return new HTTPObject(hdrobj, null);
		
		// Otherwise read the body
		char [] bodytmp = new char[Integer.parseInt(hdrobj.attributes.get("content-length"))];
		System.err.println("Starting reading bis");
		byte [] bodytmp1 = new byte[bodytmp.length];
		ibsr.read(bodytmp);
		System.err.println("End reading bis");
		
		int i=0;
		for(char x:bodytmp)
		{
			bodytmp1[i++] = (byte) x;
		}
		
		return new HTTPObject(hdrobj, IOUtils.b2B(bodytmp1));
	}

}
