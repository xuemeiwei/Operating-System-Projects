import java.io.BufferedReader;
import java.io.IOException;

public class Reader extends Thread{
	BufferedReader bufferedReader;
	StringBuffer buffer1;
	int flag = 8;// Ensure the termination after one successful read
	
	public Reader(BufferedReader bufferedReader, StringBuffer buffer1) throws IOException{
		this.bufferedReader = bufferedReader;
		this.buffer1 = buffer1;
		//System.out.println("buffer1 before read runs: " + buffer1);
	}
	
	// Read from text file and store them into buffer1
	public void read() throws IOException{
		String line = bufferedReader.readLine();
		buffer1.delete(0, buffer1.length());
		buffer1.append(line);
	}
	
	public void run() {	
		while(flag-- != 0) {
//			try {
//				read();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {	
				Semaphores.readBufferEmpty.acquire();
				read();
				// Set readBuffer to 1 then Print can run.
				Semaphores.readBufferFull.release();
			} catch (IOException | InterruptedException e) {				
				e.printStackTrace();
			}
		}
	}
}
