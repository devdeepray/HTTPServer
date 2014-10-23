import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;


public class HTTPSession extends Thread{
	
	Socket socket;
	
	public HTTPSession(Socket socket) throws SocketException{
		this.socket = socket;
		socket.setReceiveBufferSize(1000000);
	}
	
	public void run() {
		try{
			// Pipelining the requests and data sending
			// Create a buffered reader and buffered writer and a buffered output stream for binary
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			
			HTTPReceiverThread hrt = new HTTPReceiverThread(bis);
			HTTPProcessorThread hpt = new HTTPProcessorThread(hrt);
			HTTPSenderThread hst = new HTTPSenderThread(bos, hpt);
			
			hrt.start();
			hpt.start();
			hst.start();
			
		}
		catch (Exception e){
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println(System.currentTimeMillis());
			return;
		}
		/*try {
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/

		
	}
}
