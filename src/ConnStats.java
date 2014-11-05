/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: Stores connection stats for one connection. 
 * Data stored includes total bytes transfered, total requests processed, 
 * start time and close time
 */


public class ConnStats
{
	public String IP;
	public int portNum;
	public long startTime;
	public long closeTime;
	public int requestsProcessed;
	public long bytesReceived;
	public long bytesSent;
	
	public ConnStats(String IP, int portNum, long startTime)
	{
		this.IP = IP;
		this.portNum = portNum;
		this.startTime = startTime;
		requestsProcessed = 0;
		bytesReceived = 0;
		bytesSent = 0;
	}
	
	public String getStringRep() 
	{
		String ret = new String();
		ret += IP + "\t" + portNum + "\t" + startTime + "\t" + closeTime + "\t" + requestsProcessed + "\t" + bytesReceived + " " + bytesSent + "\n";
		return ret;
	}
}
