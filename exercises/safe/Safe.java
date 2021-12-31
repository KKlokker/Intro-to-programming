public class Safe {
		private boolean locked;
		private int[] code;
		private String secret;
		private int attemptIndex;

		//Constructs a safe with the given code
		public Safe(int[] code) {
			locked = true;
			this.code = code.clone();
			secret = "";
			attemptIndex = 0;	
		}

		//Returnsif the safe is locked
		public boolean locked() {
			return locked;
		}
		
		//Sets the secret equal to the secret if the safe is unlocked
		public void store(String secret) {
			if(!locked)
				this.secret = secret;
		}

		//Returns an empty string if not unlocked otherwise returns secret
		public String contents() {
			if(!locked)
				return secret;
			return "";
		}

		//Inserts a new int to the code at the end. No need for unlock according to contract
		public void insert(int n) {
			int[] newCode = new int[code.length + 1];
			for(int i = 0; i < code.length; i++)
				newCode[i] = code[i];
			newCode[code.length] = n;
			code = newCode;
		}
		
		//Reset the current unlock attempt
		public void reset() {
			attemptIndex = 0;
		}
		
		//Takes an int and check if it is equal to the code if not resets attempt and locs
		public void unlock(int n) {
			if(code[attemptIndex] == n) {
				attemptIndex++;
				if(attemptIndex == code.length)
					locked = false;
			}
			else {
				attemptIndex = 0;
				locked = true;
			}
		}	

}
