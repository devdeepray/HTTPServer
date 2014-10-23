import java.io.IOException;


public class MainServer {
	// This is the main server class. It initiates various modules and starts listening
	
	public static void main(String arg[]) throws IOException {
		// Variable declarations
		ConnectionListener connlistener = null;
		int port = 9090;
		// Load up the file cache
		//FileCache.loadResponsePages();
		// Start the connection listener on set port
		connlistener = new ConnectionListener(port);

		System.err.println("Listening on port 9090");
		connlistener.beginListening();		
	}
	
}
