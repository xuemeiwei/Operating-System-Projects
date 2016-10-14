
public class Converter extends Thread{
	StringBuffer buffer1;
	StringBuffer buffer2;
	int flag = 8;// Ensure the termination after one successful copy from buffer1 to buffer2 
	public Converter(StringBuffer buffer1, StringBuffer buffer2) {
		this.buffer1 = buffer1;
		this.buffer2 = buffer2;
	}
	
	// Copying data in buffer1 to buffer2
	public void convert() throws InterruptedException {
		buffer2.delete(0, buffer2.length());
		buffer2.append(buffer1.toString());
	}
	
	public void run() {
		while(flag-- != 0) {
			try {
				// Can be executed only when readBuffer = 1
				
				Semaphores.readBufferFull.acquire();
				Semaphores.writeBufferEmpty.acquire();	
				
				// Set writeBuffer to 1 then Print can run.
				convert();
				
				Semaphores.readBufferEmpty.release();
				Semaphores.writeBufferFull.release();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
