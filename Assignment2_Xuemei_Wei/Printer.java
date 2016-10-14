
public class Printer extends Thread{
	StringBuffer buffer2;
	int flag = 8; // Ensure the termination after successful print
	public Printer(StringBuffer buffer2) {
		this.buffer2 = buffer2;
	}
	
	//Print data from buffer2;
	public void print() {		
		System.out.println("Every Step of Print result: " + buffer2.toString());	
	}
	public void run() {
		while(flag-- != 0) {
//			print();
			try {
				// Can be executed only when writeBuffer = 1, namely after Converter sets it to 1
				Semaphores.writeBufferFull.acquire();
				print();	
				Semaphores.writeBufferEmpty.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}		
	}
}
