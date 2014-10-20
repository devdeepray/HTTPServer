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
		try
		{
			// Create a buffered reader and buffered writer and a buffered output stream for binary
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bos));
			InputStreamReader isr = new InputStreamReader(bis);
			System.out.println("Recevied ");
			
				System.err.println("Start to receive");
				HTTPObject hto = HTTPReceiver.receive(bis, isr);
				if(hto == null) return;
				System.err.println("End receive");
				System.err.println("Start to process");
				HTTPObject response = HTTPRequestProcessor.getResponse(hto);
				System.err.println("End process");
				System.err.println("Start to send");
				HTTPSender.send(response, bos, writer);
				System.err.println("End send");
			// Now we need to process hto
		}
		catch (Exception e)
		{
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
		try {
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}
}
