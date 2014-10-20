import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class HTTPSender {

	public static void send(HTTPObject response, BufferedOutputStream bos, BufferedWriter writer) throws IOException {
		// TODO Auto-generated method stub
		writer.write(response.header.version + " " + response.header.action + " " + response.header.param + "\r\n");
		for(String x : response.header.attributes.keySet())
		{
			writer.write(x + " : " + response.header.attributes.get(x) + "\r\n");
			
		}
		writer.write("\r\n");
		writer.flush();
		bos.write(IOUtils.B2b(response.body));
		bos.flush();
	}
	
}
