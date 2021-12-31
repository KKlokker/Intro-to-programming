public class permutationGen {
	 	public static void main(String[] args){
			permutation("", "stacks");		 	
		}
		
		public static void permutation(String start, String perm) {
			if(perm.length() == 0)
				System.out.println(start);
			else 
				for(int i = 0; i < perm.length(); i++)
					permutation(start+perm.charAt(i), perm.substring(0,i) + perm.substring(i+1 ,perm.length()));	
		}

}
