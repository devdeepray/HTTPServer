import java.io.IOException;


public class MainServer {
	// This is the main server class. It initiates various modules and starts listening
	
	public static int debugCode = 0x1;
	
	public static void main(String arg[]){
		// Variable declarations
		ConnectionListener connlistener = null;
		
		// Start the connection listener on set port
		try{
			connlistener = new ConnectionListener(ServerSettings.getPortNumber());
		}
		catch (IOException e){
			Debug.print("Error starting socket", debugCode);
			return;
		}
		
		
		Debug.print("Listening on port " + ServerSettings.getPortNumber(), debugCode);
		try {
			connlistener.beginListening();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Debug.print("Unhandled error while listening on socket. Should never occur", debugCode);
		}		
	}
	
}
