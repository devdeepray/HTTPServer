import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;


public class HTTPSession implements Runnable{
	
	Socket socket;
	public static int debugCode = 0x4;
	
	public HTTPSession(Socket socket) throws SocketException{
		this.socket = socket;
	}
	
	public void closeSocket()
	{
		try {
			socket.close();
		} catch (IOException e1) {
			System.err.println("Couldnt close socket. Still exiting");
		}
	}
	
	public void run() {
		
		// Start processing a connection. Cant throw exceptions from here, all must be handled
		
		InputStream is = null;
		OutputStream os = null;
		
		// Create a buffered reader and buffered writer and a buffered output stream for binary
		try{
			is = socket.getInputStream();
			os = socket.getOutputStream();
		}catch (IOException e){
			Debug.print("IOException while creating streams from socket", debugCode);
			closeSocket();
			return;
		}
			// Pipelining the requests and data sending
			if(ServerSettings.isPiped()){

				HTTPReceiverRunnable hrt = new HTTPReceiverRunnable(is);
				HTTPProcessorRunnable hpt = new HTTPProcessorRunnable(hrt);
				HTTPSenderRunnable hst = new HTTPSenderRunnable(os, hpt);
				Debug.print("Starting all pipe threads", debugCode);
				ThreadPool.executeSessionThread(hrt);
				ThreadPool.executeSessionThread(hpt);
				hst.run();
				Debug.print("third thread exited. Shutting down things.", debugCode);
			}
			
			else{
				
				Debug.print("Processing one http packet", debugCode);
				try {
					HTTPSenderUtils.send(HTTPRequestProcessor.getResponse(HTTPReceiverUtils.receive(is)), os);
				} catch (Exception e) {
					Debug.print("Finished processing one packet", debugCode);
				}
			}
		
		
		System.err.println("HTTP Session ended. Closing down connection and other stuff");
		closeSocket();
		return;

		
	}
}
