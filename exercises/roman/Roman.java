import java.util.scanner;
public class Roman {
		
	 	public static void main(String[] args){
			
			Scanner sc = new Scanner(System.in); 
			int input;
			do {
				printMenu();
				input = sc.nextInt();	
				sc.nextLine(); //consumes <enter> from above
				switch(input) {
					case 0: {
							System.out.println("Goodbye!");
							break;
					}
					case 1: {
						       System.out.print("What roman numeral do you want converted?: ");
						       int converted = romanToNum(sc.nextLine());
						       System.out.println("The number is " + converted);
						       break;
					}
					case 2: {
							System.out.print("What number do you wanted converted to roman numeral?: ");
							String converted = numToRoman(sc.nextInt());
							System.out.println("The number in roman numeral is " + converted);
							break;
					}
					case 3: {
							System.out.println("Which roman numerals do you want added?");
							String num1 = sc.nextLine();
							System.out.println("+");
							String num2 = sc.nextLine();
							String addition = add(num1,num2);
							System.out.println("The numbers added together is " + addition);
							break;
					}
					case 4: {
							System.out.println("Which roman numerals do you want subtracted?");
							String num1 = sc.nextLine();
							System.out.println("-");
							String num2 = sc.nextLine();
							String difference = diff(num1, num2);
							System.out.println("The difference is " + difference);
							break;
					}
					case 5: {
							System.out.print("What roman numeral do you want validated: ");
							String roman = sc.nextLine();
							boolean valid = isRomanNum(roman);
							String result = (valid) ? "The roman numeral is valid" : "It is an invalid roman numeral";
							System.out.println(result);
							break;
					}
					default: {
							 System.out.println("Invalid option");
							 break;
					}
				}
			}
			while(input != 0);
		}
		//Prints menu
		private static void printMenu(){
			System.out.println("Which option do you want?");
			System.out.println("1. Convert roman numeral to number");
			System.out.println("2. Convert number to roman numeral");
			System.out.println("3. Addition on two roman numerals");
			System.out.println("4. Subtraction on two roman numerals");
			System.out.println("5. Validate roman numeral");
			System.out.println("Exit the program by writing 0");
		}
		//Returns the number of roman numeral
		private static int romanToNum(String num) {
			if(!isRomanNum(num)){
				System.out.println("Invalid input");
				return 0; // non valid roman numeral
			}
			int sum = 0;
			for(int i = 0; i< num.length(); i++)
				sum = sum + romanToNum(num.charAt(i));	
			return sum;
		}
		
		//Convert roman numeral to digit
		private static int romanToNum(char roman) {
			int converted;
			switch(roman){
				case 'I': {
					converted = 1;
					break;
				}
				case 'V': {
					converted = 5;
					break;
				}
				case 'X': {
					converted = 10;
					break;
				}
				case 'L': {
					converted = 50;
					break;
				}
				case 'C': {
					converted = 100;
					break;
				}
				case 'D': {
					converted = 500;
					break;
				}
				case 'M': {
					converted = 1000;
					break;
				}
				default:{
					  converted = 0;
					  break;
				}
			}
			return converted;
		}

		//Returns the string version of a number in roman numerals
		private static String numToRoman(int num){
			String roman = "";
			String[] romanNumerals = new String[]{"M", "D", "C", "L", "X", "V", "I"};
			int[] romanValues = new int[]{1000, 500, 100, 50, 10, 5, 1};
			for(int i = 0; i<romanNumerals.length;i++){
				int amount = num / romanValues[i];
				roman = roman + romanNumerals[i].repeat(amount);
				num = num % romanValues[i];
			}
			return roman;
		}

		//Adds two roman numerals together and returns the result in roman numeral
		private static String add(String num1, String num2){
			String sorted = romanMergeSort(num1, num2);
			sorted = shortenRoman(sorted);
			String sortedLeftToRight = "";
			for(int i = sorted.length()-1; 0<=i;i--)
				sortedLeftToRight = sortedLeftToRight + sorted.charAt(i);
			return sortedLeftToRight;
		}
		
		//Shorten roman numeral
		private static String shortenRoman(String num) {	
			String[] romanEqualPatterns = new String[]{"IIIII","VV","XXXXX","LL","CCCCC","DD"};
			String[] romanEquals = new String[]{"V","X","L","C","D","M"};
			for(int i = 0; i<romanEqualPatterns.length;i++)
				num = num.replace(romanEqualPatterns[i],romanEquals[i]);
			return num;
		}

		//Takse two roman numerals and sort them into one roman numeral
		private static String romanMergeSort(String num1, String num2) {
			int i = num1.length()-1;
			int j = num2.length()-1;
			String sorted = "";
			while(0<=i && 0<=j) {
				int numeral1 = romanToNum(num1.charAt(i));
				int numeral2 = romanToNum(num2.charAt(j));
				if (numeral1 < numeral2){
					sorted = sorted + num1.charAt(i);
					i--;
				}
				else {
					sorted = sorted + num2.charAt(j);
					j--;
				}
			}
			while(0<=i){
				sorted = sorted + num1.charAt(i);
				i--;
			}
			while(0<=j) {
				sorted = sorted + num2.charAt(j);
				j--;
			}
			return sorted;
		}

		//Subtracts two roman numaeral. Precon. num1>=num2
		private static String diff(String num1, String num2){
			int amountOfSingles = romanToNum(num1)-romanToNum(num2);
			String romanNumeral = "I".repeat(amountOfSingles);
			romanNumeral = shortenRoman(romanNumeral);
			return romanNumeral;
		}
		
		//Returns if roman numeral only contains valid characters and in order
		private static boolean isRomanNum(String s) {
			String romanEquals = "MDCLXVI";
			for(int i = 0; i<s.length()-1; i++){
				char numeral1 = s.charAt(i);
				char numeral2 = s.charAt(i+1);
				int indexOfNumeral1 = romanEquals.indexOf(numeral1);
				int indexOfNumeral2 = romanEquals.indexOf(numeral2);
				if(indexOfNumeral1 == -1 || indexOfNumeral2 == -1 || indexOfNumeral2 < indexOfNumeral1)
					return false;
			}
			return true;
		}


}
