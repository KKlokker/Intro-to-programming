//Stars is used to work with methods and their functionality
public class Stars {
	 	public static void main(String[] args){
			//stars(4);
			//lineOfStars(4);
			triangleStar(5);
		}
		//Print a line of stars, where n is the height of the line. Precondition n is positive	
		private static void stars(int n){
			int i = 0;
			while (i<n){
				System.out.println("*");
				i = i + 1;
			}
		}
		//Prints half a triangle of starts with the height n. Precondition n is positive
		private static void lineOfStars(int n){
			int i = 0;
			while(i<n){
				System.out.println("8".repeat((i+1)));
				i = i+1;
			}
		}
		//prints a triangle with the height n. Precondition n is positive
		private static void triangleStar(int n){
			int i = 0;
			while(i!=n){
				String blankSpace = " ".repeat(n-i-1);
				String stars = "*".repeat(i*2+1);
				System.out.println(blankSpace+stars+blankSpace);
				i = i + 1;
			}
		}
}
