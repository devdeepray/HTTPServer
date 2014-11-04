/** 
 * Author: Devdeep Ray
 * Project: HTTPServer (Networks)
 * Description: This class is for printing debug messages in various layers. 
 * Each class has a layer number and we can enable debugging for any set of layers.
 */

public class Debug 
{
	public static void print(String string, int debugCode) 
	{
		if((ServerSettings.getDebugLevel() & debugCode) != 0) // If debug code matches server debug level
		{
			System.err.println(string);
		}
	}	
}
