public class PalindromeChecker {
	 	public static void main(String[] args){
			System.out.println(isPalindrome("hsejAjeh"));
		}
		
		public static boolean isPalindrome(String palindrome) {
			int length = palindrome.length();
			if(length == 1 || length == 0)
				return true;
			if(palindrome.charAt(0)==palindrome.charAt(length-1))
				return isPalindrome(palindrome.substring(1,length-1));
			else
				return false;
		}

}
