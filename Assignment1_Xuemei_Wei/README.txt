Ques 2. Implement a program to count the frequency of words in a text file. The text file is partitioned into N segments. Each segment is processed by a separate thread that out- puts the intermediate frequency count for its segment. The main process waits until all the threads complete; then it computes the consolidated word-frequency data based on the individual threads’ output.
Solution:
Source Code file:  	wordCount.java; subWordCount.java(In the same package)Input File: 		testWords.txt // Store the words to compute frequencyOutput File: 		results.txt  // Store the words and their frequency.wordCount.java: The main thread to merge the results of each thread.
subWordCound.java: The implementation of each thread.Steps: 	1. Convert the input text file to a String Array;
	2. Separate the String Array to N segments;
	3. Let each thread process one segment and put all the tasks in the future task 	   queue;
	4. Use a HashMap to store the total results of each thread.
Main data structure used is HashMap<String, Integer> to store the frequency of each word. For each thread compute its word frequency then merge them to a total HashMap.

Here N = 4 is used as an example. You can modify the value of N as needed.