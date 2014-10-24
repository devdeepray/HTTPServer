import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class HTTPSenderUtils {

	public static void send(HTTPObject response, BufferedOutputStream bos) throws Exception {
		// TODO Auto-generated method stub
		bos.write((response.header.version + " " + response.header.action + " " + response.header.param + "\r\n").getBytes());
		for(String x : response.header.attributes.keySet())
		{
			bos.write((x + ": " + response.header.attributes.get(x) + "\r\n").getBytes());
			
		}
		bos.write("\r\n".getBytes());
		bos.write(IOUtils.B2b(response.body));
		bos.flush();
	}
	
	
	
}
