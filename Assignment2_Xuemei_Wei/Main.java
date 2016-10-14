import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Main Thread");
		BufferedReader br = new BufferedReader(new FileReader("/Users/xuemeiwei/Desktop/testForHomework2.txt"));
		
		//Store the data which is read from the text file
		StringBuffer buffer1 = new StringBuffer();
		//Store data from buffer1 and to be printed
		StringBuffer buffer2 = new StringBuffer();		
		
		// Create three processes, namely: Reader, Converter and Printer
		Reader reader = new Reader(br, buffer1);				
		Converter converter = new Converter(buffer1, buffer2);				
		Printer printer = new Printer(buffer2);
		
		// Start the three processes, Reader, Converter and Printer
		reader.start();
		System.out.println("Reader starts");
		converter.start();
		System.out.println("Converter starts");
		printer.start();
		System.out.println("Printer starts");
	}
}
