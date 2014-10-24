
public class Pipe extends Thread{
	Runnable runnable;
	Pipe next;
	public void run()
	{
		runnable.run();
	}
}
