import java.util.Scanner;
public class Calculator {

		private static List tokens;
		
	 	public static void main(String[] args){
			String expression = "1 + 3 * 3";
			Scanner sc = new Scanner(System.in);
			String input;
			while(sc.nextLine() != "0"){
				System.out.println("Expression:");
				expression = sc.nextLine();
				tokens = new List(expression.split(" "));
				System.out.println(parseE().toString());
			}
		}

		//Parse expression if plus minus or nothing
		private static Fraction parseE() {
			Fraction m = parseM();
			if(tokens.isEmpty() || tokens.head().equals(")")) return m;
			String nextToken = tokens.head();
			tokens.tail();
			Fraction e = parseE();
			if (nextToken.equals("+"))
				m.add(e);
			else if (nextToken.equals("-"))
				m.subtract(e);
			return m;
		}
		
		
		//Parse expression if multiply or divide or nothing
		private static Fraction parseM() {
			Fraction t = parseT();
			if(tokens.isEmpty()) return t;
			String nextToken = tokens.head();
			if (nextToken.equals("/")) {
				tokens.tail();
				Fraction m = parseM();
				t.divide(m);
			}
			else if (nextToken.equals("*")) {
				tokens.tail();
				Fraction m = parseM();
				t.multiply(m);
			}
			return t;
		}

		//Parse expression in parenthesisi or int
		private static Fraction parseT() {
			String nextToken = tokens.head();
			tokens.tail();
			if(nextToken.equals("(")){
				Fraction e = parseE();
				tokens.tail();
				System.out.println("The parenthesis is " + e);
				return e;
			}
			else {
				int n;
				try {
					if (57 < nextToken.charAt(0) || nextToken.charAt(0) < 48)
						throw new CharException();
					n = Integer.parseInt(nextToken);
				}	
				catch (CharException e) {
					System.out.println("You are not using numbers!");
					n = 0;
				}
				catch (NumberFormatException e) { 
					System.out.println("Wrong format");
					n = 0;
				}
				return new Fraction(n);
			}
		}
}
