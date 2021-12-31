public class SolvingEquations {
	 	public static void main(String[] args){
			double a = 0;
		 	double b = 1000;
			double c = 500;
			while(function(c)>0.0001 || function(c) < -0.0001){
				c = (a+b)/2;
				if (function(c)*function(a)<0)
					c = b;
				else 
					a = c;
			}
			System.out.println(c);
		}
			

		private static double function(double n){
			return 2*n-8;
		}
}
