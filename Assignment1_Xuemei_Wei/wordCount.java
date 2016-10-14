import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.lang.String;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class wordCount {
	private ExecutorService exec;//
	private int N;
	private List<Future<Map<String, Integer>>> tasks = new ArrayList<Future<Map<String, Integer>>>();
	public wordCount() {
		N = 4;
		exec = Executors.newFixedThreadPool(N);
	}
	/*The function to merge all the results of each thread
	 * Input: All the words in the text file which all are stored in a String Array
	 * Output: HashMap to store each word and its frequency
	 * Steps: 1. Separate all the words into N Segments and let N threads process each segment
	 *        2. Merge the results of each thread into a total HashMap*/
	
	public Map<String, Integer> sum(String[] input) {
		// Separate all the words to N segments
		for (int i = 0; i < N; i++) {
			int increment = input.length / N + 1;
			int start = increment * i;
			int end = increment * i + increment;
			if (end > input.length)
				end = input.length;
			subWordCount subCalc = new subWordCount(input, start, end);
			FutureTask<Map<String, Integer>> task = new FutureTask<Map<String, Integer>>(subCalc);
			tasks.add(task);
			if (!exec.isShutdown()) {
				exec.submit(task);
			}
		}
		
		// Use a HashMap to store the consolidated result of all threads
		Map<String, Integer> result = new HashMap<String, Integer>();
		for (Future<Map<String, Integer>> task : tasks) {
			try {
				// Merge result with each thread
				Map<String, Integer> subSum = task.get();
				for(String key: subSum.keySet()){
				   if(result.containsKey(key)){
					   result.put(key, result.get(key)+subSum.get(key));
				   }else{
					   result.put(key, subSum.get(key));
				   }
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public void close() {
		exec.shutdown();
	}	
	public static void main(String[] args) throws InterruptedException, IOException {
		//Read the input text file and convert the text file to String Array
		BufferedReader br = new BufferedReader(new FileReader("/Users/xuemeiwei/Desktop/testWords.txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();
		while(line != null) {
			sb.append(line);
			line = br.readLine();
		}
		String[] arr = sb.toString().split(" ");
		br.close();
		
		// Main thread to compute the frequency
		System.out.println("Main Thread"+ Thread.currentThread().getId()+ " Begins!");
		wordCount calc = new wordCount();  
		Map<String, Integer> sum = calc.sum(arr);// Store the total frequency of each world
		System.out.println("The total results are:");
		System.out.println(sum);// Print the total frequency
		calc.close();
		System.out.println("Main Thread"+ Thread.currentThread().getId() + " Completed!");
		
		// Write the results to output file
		FileWriter fw = new FileWriter("/Users/xuemeiwei/Desktop/results.txt");
		BufferedWriter out = new BufferedWriter(fw);
		Iterator<Entry<String, Integer>> it = sum.entrySet().iterator();
		out.write("In total the frequency of each word is: \n");
		while(it.hasNext()) {
			Map.Entry<String, Integer> pair = it.next();
			out.write(pair.getKey() + ":" + pair.getValue() +"\n");
		}
		out.close();		
	}
}

