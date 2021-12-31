public class Numbers {
	 	public static void main(String[] args){
			System.out.println(gcd(2322,654));	 	
		}

		private static int gcd(int m,int n){
			int a = m;
			int b = n;
			int q,r;
			while (b>0){
				q=a/b;
				r=a-b*q;
				a=b;
				b=r;
			}
			return a;
		}
}
