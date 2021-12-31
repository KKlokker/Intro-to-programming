public class FindingZeroes {
	 	int n;	
		int formulaIndex;

		public static void main(String[] args){
			FindingZeroes test = new FindingZeroes(1);
			test.setFormula(1);
			findZero(test);
			System.out.println(test.n);	
		}

		//Obejct constructor
		private FindingZeroes(int startValue) {
			n = startValue;
		}	
		
		//Set object index of formula 
		private void setFormula(int n) {
			formulaIndex = n;
		}

		//Returns objects function value
		private int function(int x) {
			n = x;
			int y;
			switch (formulaIndex){
				case 1:
			 		y=8-2*x;
					break;
				case 2:
					y=2*x*x-2*x;
					break;
				default:
					y=x;
					break;
			}
			return y;
		}

		//This functions test natural values from 0 to 1000 and test if the FindZero objects function returns 0
		private static void findZero(FindingZeroes math){
			int i = 1;
			while(math.function(i) != 0 && i < 1000){
				i++;
			}
		}
}
