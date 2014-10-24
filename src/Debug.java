
public class Debug {

	public static void print(String string, int debugCode) {
		if((ServerSettings.getDebugLevel() & debugCode) != 0)
			System.out.println(string);
		
	}

	
}
