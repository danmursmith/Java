package project3;
import java.util.*;
import java.io.*;
/* WORD LADDER Main.java
 * EE422C Project 3 submission by
 * Dan Smith
 * Dms3665
 * 
 * James Zhang
 * jz7436
 * 
 * Slip days used: <0> 
 * Fall 2015
 */

public class Main {
	public static void main(String[] args) {
		boolean cont = true;
		while (cont){
			Scanner kb = new Scanner(System.in);	
			String input1 = kb.next();
			input1 = input1.toUpperCase();
			if (input1.charAt(0) == '/'){
				if(input1.endsWith("QUIT")){
					kb.close();
					cont = false;
				}
				else
					System.out.println("invalid command " + input1.toLowerCase());
			}
			else{
				String input2 = kb.next();
				input2 = input2.toUpperCase();
				printLadder(getWordLadder(input1, input2), input1, input2);
			}
		}
	}

	public static class Node {																	//creates node to carry word and ladder
		String word;
		ArrayList<String> ladder;
		
		public Node(String word1, ArrayList<String> ladder1){
			this.word = word1;
			this.ladder = ladder1;
		}
	}
	
	public static ArrayList<String> getWordLadder(String start, String end) {
		Set<String> dict = makeDictionary();
		//System.out.println("break");
		if ((isValidInput(start, dict) == false) || (isValidInput(end, dict) == false) || (start.equals(end))){		//create all needed objects
			return new ArrayList<String>();
		}
		ArrayList<String> alreadyUsed = new ArrayList<String>();
		Queue<Node> fifo = new LinkedList<Node>();
		ArrayList<String> first = new ArrayList<String>();
		
		String newWord;
		
		first.add(start);					
		Node firstNode = new Node(start,first);
		fifo.add(firstNode);										//add start word
		alreadyUsed.add(start);
		
		while(true){
			if(fifo.isEmpty())
				return new ArrayList<String>();
			Node current = fifo.poll();
			newWord = current.word;
			//System.out.println(newWord);
			if(newWord.equals(end))
				return current.ladder;
			for (int count = 0; count < 5; count++){								//create new word
				newWord = current.word;
				//System.out.println(newWord);
				for (char ch = 'a'; ch <= 'z'; ch++){
					char[] charNewWord = newWord.toCharArray();
					charNewWord[count] = ch;
					newWord = String.valueOf(charNewWord);
					newWord = newWord.toUpperCase();
					//System.out.println(newWord);
					if(dict.contains(newWord))										//check if valid word and not already used
						if(!alreadyUsed.contains(newWord)){
							//System.out.println(newWord);
							ArrayList<String> replace = new ArrayList<String>(current.ladder.size());
							int x = 0;
							while (x < current.ladder.size()){
								replace.add(current.ladder.get(x));
								x++;
							}
							replace.add(newWord);
							alreadyUsed.add(newWord);
							Node next = new Node(newWord, replace);
							fifo.add(next);
						}
				}
			}
		}
	}
		
	public static void printLadder(ArrayList<String> ladder, String start, String end){		//prints ladder
		if(ladder.isEmpty())
			System.out.println("no word ladder could be found between " + start.toLowerCase() + " and " + end.toLowerCase() + ".");
		else{
			int x = 0;
			System.out.println("a " + ladder.size() + "-rung word ladder exists between " + start.toLowerCase() + " and " + end.toLowerCase() + ".");
			while (x < ladder.size()){
				System.out.println(ladder.get(x).toLowerCase());
				x++;
			}
		}
	}
	
	public static boolean isValidInput(String str, Set<String> dict){					//checks is start and end words are valid
		//System.out.println(str.length());
		if(str.length() != 5)
			return false;
		if(dict.contains(str)) 	
			return true;
		else
			return false;
	}		
	
	public static Set<String>  makeDictionary () {
		Set<String> words = new HashSet<String>();
		Scanner infile = null;
		try {
			infile = new Scanner (new File("five_letter_words.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Dictionary File not Found!");
			e.printStackTrace();
			System.exit(1);
		}
		while (infile.hasNext()) {
			words.add(infile.next().toUpperCase());
		}
		return words;
	}
}



	

	

	