package com.parser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;


public class DocParser {
	
	
	/*
	 * Create stopwords' array from the txt file, downloaded from 
	 * "https://sites.google.com/site/kevinbouge/stopwords-lists"
	 */
	ArrayList<String> stopWords = new ArrayList<String>();
	

	//Compile a regex for unwanted charaters from the word. E.g. Amazon.!, Accessories|}, etc. 
	
	private final Pattern UNDESIRABLES = Pattern.compile("[\\]\\[(){},:|@'#$\"&%^*~`<>.;!?<>%]");
	
	/*
	 * Create a list of words which contains preprocessed words
	 * and create a HashMap of words that will contain the output of keywords required.
	 */
	
	HashMap<String, Integer> importantWords = new HashMap<String, Integer>();
	
	public DocParser(String mainContent, String headerContent, String paraContent){
		System.out.println("class name "+getClass());
//		Scanner sc = new Scanner(getClass().getResourceAsStream("stopwords_en.txt"));
//		while (sc.hasNext()) {
//			stopWords.add(sc.next());
//		}
//		sc.close();
		try {
			File file = new File("stopwords_en.txt");
			System.out.println(file.getAbsolutePath());
			
			//Scanner sc = new Scanner(getClass().getResourceAsStream("stopwords_en.txt"));
			Scanner sc = new Scanner(file);
			
			while (sc.hasNext()) {
				stopWords.add(sc.next());
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		fetchImportantKeyWords(mainContent, headerContent, paraContent);
		
	}
	
	
	public void fetchImportantKeyWords(String str, String header, String paragraph){
		
		Scanner sc=new Scanner(str);
		
		while(sc.hasNext()){
			int freq;
			int i = 0;
			String word=sc.next();
			
			String refinedWord = removeUndesirable(word);
			refinedWord = refinedWord.toLowerCase();
			
			/*
			 * Check whether your refinedWord is a stopword or not, if not then consider the word
			 * for keyword computation
			 */
			
			if(!checkForStopWords(refinedWord) && refinedWord.length()>1){
				
				if (!importantWords.containsKey(refinedWord)){
					
					importantWords.put(refinedWord, 1);
				}
				else{
					int updatedFreq = (Integer) importantWords.get(refinedWord);
					importantWords.put(refinedWord, ++updatedFreq);
				}		
			}
		}
		
		sc.close();

		Map sortedMap = sortByValue(importantWords);
		System.out.println("------ Here is the result for keywords and its frequency in sorted fashion ------- ");
		
		System.out.println(sortedMap+"\n");
		System.out.println("------- Our Heading String is --------");
		System.out.println(header+"\n");
		System.out.println("Keyword values that are frequent and also a substring of header/1st paragraph information are...");
		List<String> keywords = new ArrayList<String>();
		List<String> topList = new ArrayList<String>(sortedMap.keySet());
		
		// Printing top 5 values/keywords that are most relevant
		    for (int i = 0; i < 10; i++) {
		   
		    	if(header.toLowerCase().contains((String) topList.get(i)) || paragraph.toLowerCase().contains((String) topList.get(i)) ){
		    		
		    		  keywords.add(topList.get(i));
		    	}
		}
		System.out.println(keywords);
	}
	
	public static Map sortByValue(Map unsortedMap) {
		Map sortedMap = new TreeMap(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
	
		return sortedMap;
	}

	private String removeUndesirable(String s){
	
		return UNDESIRABLES.matcher(s).replaceAll("");
		    					
	}
	
	private boolean checkForStopWords(String s){
		return stopWords.contains(s);
	}

}


class ValueComparator implements Comparator {
	Map map;
 
	public ValueComparator(Map map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		int compare = valueB.compareTo(valueA);
	    if (compare == 0) {
	        compare = ((String) keyA).compareTo((String) keyB);
	    }
	    return compare;
	
	}
}
