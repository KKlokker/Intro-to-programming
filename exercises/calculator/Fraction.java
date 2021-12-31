public class Fraction {
	 	private int divident;
		private int divisor;
		
		public static void main(String[] args){
			Fraction f = new Fraction(4);
			Fraction f2 = new Fraction(8);
			f.add(f2);
			f.multiply(f2);	
			f.divide(f2);
			System.out.println(f.toString());
			Fraction test = new Fraction(8,3);
			System.out.println(test.integerPart() + " " + test.properPart() + " " +test.value());		
		
		}

		public Fraction() {
			this(1,1);
		}
		
		public Fraction(int divident) {
			this(divident,1);
		}

		public Fraction(int divident, int divisor) {
			this.divident = divident;
			this.divisor = divisor;
		}
		
		//Find greates common divisor between int a and b. Precon. a and b are greater than 1
		private int gcd(int a, int b) {
			int R = a % b;
			return (R==0) ? b : gcd(b,R);
		}
		
		//Finds the most simple version of given fraction
		public void simplify(Fraction f) {
			int gcd = gcd(f.divident, f.divisor);
			f.divident = f.divident/gcd;
			f.divisor = f.divisor/gcd;	
		}

		//Adds two fractions together
		public void add(Fraction f) {
			this.divisor = f.divisor * this.divisor;
			this.divident = this.divident*f.divisor +f.divident;
		}

		//Subtracts two fractions
		public void subtract(Fraction f) {
			this.divisor = f.divisor * this.divisor;
			this.divident = this.divident*f.divisor - f.divident;
		}

		//Returns a string with the fraction in string format
		public String toString() {
			return this.divident + "/" + this.divisor;
		}
		
		//Multiplies two fractions
		public void multiply(Fraction f) {
			this.divisor = this.divisor*f.divisor;
			this.divident = this.divident*f.divident;
		}

		//Divide two fractions
		public void divide(Fraction f) {
			this.divident = this.divident*f.divisor;
			this.divisor = this.divisor*f.divident;
		}

		//Returns a double with the approx of fraction value
		public double value() {
			return 1.0*this.divident/this.divisor;
		}

		//Returns full integers in the fractions
		public int integerPart() {
			return this.divident/this.divisor;
		}

		//Returns fractions without any whole numbers in it
		public Fraction properPart() {
			Fraction f = this.copy();
			f.divident = f.divident-f.integerPart()*f.divisor;
			return f;
		}

		//Copies a fraction
		public Fraction copy() {
			Fraction copy = new Fraction(this.divident, this.divisor);
			return copy;
		}
		
		//Checks if the fractions is equal to the object.
		public boolean equals(Object other) {
			if(other == null)
				return false;
			if(other == this)
				return true;
			if(!(other instanceof Fraction))
				return false;
			Fraction f = (Fraction) other;
			return (f.divisor == this.divisor && f.divident == this.divident);
		}
}
