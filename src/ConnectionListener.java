import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener {

	public static int debugCode = 0x2; // Debug filter
	ServerSocket serversocket = null; // Server socket that listens for connections
	public ConnectionListener(int port) throws IOException {
		
		// Initiate a server socket to listen to tcp connections on given port
		serversocket = new ServerSocket(port);
	}

	public void beginListening() throws IOException {
		
		// Begins listening on the socket in an infinite loop.
		// After accepting a connection, creates a HTTPSession and data transfers begin
		while(true){
			// Infinite loop. Never supposed to exit.
			Debug.print("Trying to accept incoming TCP connection", debugCode);
			Socket socket = serversocket.accept(); // Blocking till incoming connection comes
			
			socket.setSoTimeout(ServerSettings.getSoTimeout()); // Set timeout of socket
			Debug.print("TCP connection established", debugCode); 
			HTTPSession httpsession = new HTTPSession(socket); // Create new HTTPSession to process connection
			
			if(ServerSettings.isMultiThreaded()){
				
				Debug.print("Start http session multithreaded", debugCode);
				ThreadPool.executeSessionThread(httpsession);
				Debug.print("Started http session multithreaded", debugCode);
			}
			
			else{
				
				Debug.print("Start http session", debugCode);
				httpsession.run();
				Debug.print("End http session", debugCode);
			}
			
		}
		
	}

}
