import java.util.HashMap;

public class HTTPObject {
	// Member variables
	public HTTPHeader header;
	public Byte [] body;
	
	public HTTPObject(HTTPHeader header, Byte[] body) {
		this.header = header;
		this.body = body;
	}

	public HTTPObject() {
		// TODO Auto-generated constructor stub
		header = new HTTPHeader();
		body = null;
	}
	
	public static HTTPObject nullHTTPObject(){
		HTTPObject tmp = new HTTPObject();
		tmp.header = null;
		return tmp;
	}
}
