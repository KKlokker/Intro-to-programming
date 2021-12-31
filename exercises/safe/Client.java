public class Client {
	 	public static void main(String[] args){
			int[] code = new int[]{3,5,7,4,1,2};
			Safe test = new Safe(code);
			for(int n: code)
				test.unlock(n);
			System.out.println(test.locked());
			test.store("Hidden message");
			test.reset();
			test.insert(9);
			int[] code2 = new int[]{3,5,7,4,1,2,9};
			for(int n: code2)
				test.unlock(n);
			System.out.println(test.contents());	
					
		}
}
