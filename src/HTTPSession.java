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
			
			System.err.println("Starting all pipe threads");
			hrt.start();
			hpt.start();
			hst.start();
			System.err.println("Started pipe. Waiting for pipe threads to exit.");
			hrt.join();
			hpt.join();
			hst.join();
			System.err.println("third thread exited. Shutting down things.");
			throw new Exception();
		}
		catch (Exception e){
			// Ignore exception as i am using it to exit
		}
		System.err.println("HTTP Session ended. Closing down connection and other stuff");
		try {
			socket.close();
		} catch (IOException e1) {
			System.err.println("Couldnt close socket. Still exiting");
		}
		return;

		
	}
}
