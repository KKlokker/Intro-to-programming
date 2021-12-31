public class Move {
		
	private int from;
	private int to;

	/**
	 * Constructs a new Move.
	 * @param from start position of move.
	 * @param to end position of move.
	 */
	public Move(int from, int to) {
		this.from = from;
		this.to = to;
	}
		
	/**
	* @return start position of move.
	*/
	public int from() {
		return from;
	}
		
	/**
	 * @return end position of move
	 */
	public int to() {
		return to;
	}
}