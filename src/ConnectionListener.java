import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ConnectionListener {

	ServerSocket serversocket = null;
	public ConnectionListener(int port) throws IOException {
		// Initiate a server socket to listen to tcp connections on given port
		serversocket = new ServerSocket(port);
	}

	public void beginListening() throws IOException {
		// Begins listening on the socket in an infinite loop.
		// After accepting a connection, creates a HTTPSession and data transfers begin
		while(true)
		{
			System.err.println("Trying to accept incoming TCP connection");
			Socket socket = serversocket.accept();
			socket.setSoTimeout(5000);
			System.err.println("TCP connection established");
			//socket.setSoTimeout(10);
			HTTPSession httpsession = new HTTPSession(socket);
			//System.err.println("Closing down server socket");
			//serversocket.close();
			System.err.println("Start http session");
			httpsession.start();
			System.err.println("End http session");
		}
	}

}
