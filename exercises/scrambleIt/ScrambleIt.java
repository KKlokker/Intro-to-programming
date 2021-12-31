import java.util.Scanner;
public class ScrambleIt {
		private static final int exit = 0;
		private static final int removeVowels = 1;
		private static final int createSpace = 2;
		private static final int reverseWords = 3;
		private static final int reverseBlocks = 4;
		private static final int caesarShift = 5;

		private static String inputString;
		private static String outputString;

	 	public static void main(String[] args){
			Scanner sc = new Scanner(System.in);
			int input;
			do {
				String inputString = getInputString();
				printMenu();
				input = sc.nextInt();
				switch(input) {
					case exit: {outputString = "Goodbye!";break;}
					case removeVowels: {outputString = removeVowels(inputString);break;}			
					case createSpace: {System.out.print("Characters between spaces: "); int n = sc.nextInt(); outputString = createSpace(inputString, n);break;}			
					case reverseWords: {outputString = reverseWords(inputString);break;}			
					case reverseBlocks: {System.out.print("Wanted block size: "); int n = sc.nextInt(); outputString = reverseBlocks(inputString, n);break;}			
					case caesarShift: {System.out.print("Wanted shift for words: "); int n = sc.nextInt(); outputString = caesarShift(inputString,n);break;}
					default: {outputString = "Unknown input";}
				}
				System.out.println("\nYour scrambled string is: " + outputString + "\n");
				sc.nextLine(); //Flush
			}
			while(input != exit);	
		}
		
		private static String getInputString() {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nWhat string do you want to work with: ");
			inputString = sc.nextLine();
			if (inputString == "") {
				System.out.println("Your string cant be empty");
				return getInputString();
			}
			return inputString;
		}
		private static void printMenu() {
			System.out.println("\nWhat do you wanna do with your string?");
			System.out.println("\t"+removeVowels + ". To remove vowels");
			System.out.println("\t"+createSpace + ". To create spaces after n length of words");
			System.out.println("\t"+reverseWords + ". To reverse words");
			System.out.println("\t"+reverseBlocks + ". To reverse blocks of text with n");
			System.out.println("\t"+caesarShift + ". To shift words by n");
			System.out.println("\t"+exit + ". To exit the program");
			System.out.print("Your option: ");
		}
		
		//Remove vowel of a string unless the whole word consist of vowel. Here y is included
		private static String removeVowels(String s){
			String output = "";
			String[] words = s.split(" ");
			for(int i = 0; i < words.length; i++) {
				String newWord = "";
				for(int j = 0; j < words[i].length(); j++)
					if (!isVowel(words[i].charAt(j)))
						newWord = newWord + words[i].charAt(j);
				if(newWord.length() != 0)
					output = output + ' ' + newWord;
				else if(newWord.length() == 0 && words[i].length() != 0)
					output = output + ' ' + words[i];
			}
			
			return output.substring(1,output.length()); //To remove the first space
		}
		
		//Returns true if charachter is a vowel
		private static boolean isVowel(char c) {
			char[] vowels = new char[]{'A','E','I','O','U','Y','a','e','i','o','u','y'};
			for(char vowel: vowels)
				if(vowel == c)
					return true;
			return false;
		}

		//Creates spaces so every word has the length n
		private static String createSpace(String s, int n) {
			String output = "";
			int counter = 0;
			for(int i = 0; i < s.length();i++){
				if(counter == n && s.charAt(i) != ' '){
					output = output + ' ';
					counter = 0;
				}
				if(counter <= n)
					output = output + s.charAt(i);
				counter++;
				if(s.charAt(i) == ' ')
					counter = 0;
			}
			return output;
		}

		//Reverse the words in the string
		private static String reverseWords(String s) {
			String output = "";
			String[] words = s.split(" ");
			for(int i = 0; i<words.length;i++)
				output = output + ' ' + reverse(words[i]);
			return output.substring(1,output.length());
		}
		
		//Reverse the string s
		private static String reverse(String s) {
			String output = "";
			for(int i = 1; i<=s.length(); i++) 
				output = output + s.charAt(s.length()-i);
			return output;
		}

		//Reverse the string in blocks of size n
		private static String reverseBlocks(String s, int n) {
			String output = "";
			String block = "";
			for(int i =0;i<s.length();i++) {
				if(i%n == 0) {
					output = output + reverse(block);
					block = "";
				}
				block = block + s.charAt(i);
			}
			output = output + reverse(block);
			return output;
		}
		
		//Shift the words in the string by n charachters
		private static String caesarShift(String s, int n) {
			String[] words = s.split(" ");
			String output = "";
			for(int i = 0; i < words.length; i++) {
				String wordShift = "";
				int wordLength = words[i].length();
				for(int j = wordLength-n%wordLength; j < wordLength*2-n%wordLength; j++)
					wordShift = wordShift + words[i].charAt(j%words[i].length());
				output = output + ' ' + wordShift;
			}
			return output.substring(1,output.length());
		}


}
