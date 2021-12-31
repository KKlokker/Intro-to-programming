public class variables {
	public static void main(String[] args) {
		int var1 = 1;
		float var2 = 1;
		double var3 = 1.22245;
		float var4 = 5;
		int var5 = 2;
		System.out.println("The int variables: " + var1 + ", " + var5 + ", the double are: " + var3 + " and the floats are: " + var2 + ", " + var4);
		int i = 4;
		int j = 9;
		int k = 19;
		double average = 0;
		average += i;
		average += j;
		average += k;
		average /= 3;
		System.out.println("The average of i,j and k is: " + average);

		int[] numbersForAverages = {4,29,12,4,2123,24};
		average = 0;
		for(int x = 0; x < numbersForAverages.length; x++) {
			average += numbersForAverages[x];
		}
		average /= numbersForAverages.length;
		System.out.println("The average of the list is: " + average);
	}
}

