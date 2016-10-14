import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
/*The implementation of each thread to count the frequency of words*/
class subWordCount implements Callable<Map<String, Integer>> {
		private String[] words;
		private int start;
		private int end;
		public subWordCount(String[] words, int start, int end) { 
	    	this.words = words;
	    	this.start = start;
			this.end = end;
	    }
		// Compute the word frequency for each segment
		public Map<String, Integer> call() throws Exception {
			Map<String, Integer> result = new HashMap<String, Integer>();
	        for(int i = start; i < end; i++){
	            if(!result.containsKey(words[i])) {
	            	result.put(words[i], 1);
	            }else{
	            	result.put((words[i]), result.get(words[i])+1);
	            }
	        }
	        System.out.println("Separate Thread"+ Thread.currentThread().getId());
	        return result;
		}
	}