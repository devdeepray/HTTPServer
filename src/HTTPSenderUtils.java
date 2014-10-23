import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class HTTPSenderUtils {

	public static void send(HTTPObject response, BufferedOutputStream bos) throws IOException {
		// TODO Auto-generated method stub
		bos.write((response.header.version + " " + response.header.action + " " + response.header.param + "\r\n").getBytes());
		for(String x : response.header.attributes.keySet())
		{
			bos.write((x + " : " + response.header.attributes.get(x) + "\r\n").getBytes());
			
		}
		bos.write("\r\n".getBytes());
		bos.write(IOUtils.B2b(response.body));
		//bos.write("".getBytes());
		bos.flush();
	}
	
	public static void send(HTTPObject response, BufferedWriter bw) throws IOException{
		
		bw.write((response.header.version + " " + response.header.action + " " + response.header.param + "\r\n"));
		for(String x : response.header.attributes.keySet())
		{
			bw.write((x + " : " + response.header.attributes.get(x) + "\r\n"));
			
		}
		bw.write("\r\n");
		bw.write(IOUtils.B2c(response.body));
		//bos.write("".getBytes());
		bw.flush();
	}
	
}
