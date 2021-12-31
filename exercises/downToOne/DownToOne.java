//DownToOne is used to work with auxilary statements and debugging
public class DownToOne {
	 	public static void main(String[] args){
			System.out.println("The number 19 took " + downToOne(19)+ " itterations");	
		}
		//downToOne is used to perform the 3+1 algorythm, Precondition n is positive
		public static int downToOne(int n){
			int i = 0;
			while(n != 1){
				if (n%2==0)
					n = n/2;
				else 
					n = n *3+1;
				System.out.println(n);
				i = i + 1;
			}
			return i;
		}
}
