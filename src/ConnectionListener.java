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
		/*while(true)
		{*/
			System.err.println("Ready to accept inccoming TCP connection");
			Socket socket = serversocket.accept();
			//socket.setSoTimeout(10);
			System.out.println("connected");
			long oldt = System.currentTimeMillis();
			HTTPSession httpsession = new HTTPSession(socket);
			serversocket.close();
			httpsession.run();
			System.err.println(System.currentTimeMillis() - oldt);
		//}
	}

}
