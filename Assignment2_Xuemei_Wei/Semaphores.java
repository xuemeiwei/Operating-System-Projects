import java.util.concurrent.Semaphore;

public class Semaphores {
	public static Semaphore readBufferEmpty = new Semaphore(1);
	public static Semaphore readBufferFull = new Semaphore(0);
	public static Semaphore writeBufferEmpty = new Semaphore(1);
	public static Semaphore writeBufferFull = new Semaphore(0);
}
