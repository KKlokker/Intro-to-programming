public class CharException extends Exception {
	public CharException() {
		super("Thats not an int!");
	}
	public CharException(String message) {
		super(message);
	}
}
