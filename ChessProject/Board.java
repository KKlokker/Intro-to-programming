public class Board {
	
	private boolean isWhite, gameOver;
	private Piece[] white, black, placement;
	
	/**
	 * Creates a bord, with the piece lineup according to the chess rules
	 * Initializes arrays white, black which contains references to piece objects of the given color
	 * Initialize placement which is references to the pieces in the black and white arrays
	 */
	public Board() {
		gameOver = false;
		white = new Piece[16];
		black = new Piece[16];
		placement = new Piece[64];
		String pieces = "RNBKQBNR";
		for(int i = 0; i < 8; i++) {
			//Black
			black[i] = new Piece(i, pieces.charAt(i), false);
			black[i+8] = new Piece(i+8, 'p', false);
			//White
			white[i+8] = new Piece(i+56, pieces.charAt(i), true);
			white[i] = new Piece(i+48, 'p', true);
		}
		for(int i = 0; i < 16; i++) {
			placement[i] = black[i];
			placement[48+i] = white[i];
		}
		isWhite = true;
	}

	/**
	 * Creates a board with the given piece placements.
	 * @param array with piece placements.
	 */
	public Board(Piece[] white, Piece[] black, boolean isWhite) {
		this.white = new Piece[16];
		this.black = new Piece[16];
		this.placement = new Piece[64];
		for(int i = 0; i < 16; i++) {
			if(white[i] != null) {
				this.white[i] = white[i].copy();
				this.placement[white[i].placement] = this.white[i];
			}
			if(black[i] != null) {
				this.black[i] = black[i].copy();
				this.placement[black[i].placement] = this.black[i];
			}
		}
		this.isWhite = isWhite;
	}

	/**
	 * Returns if this board is on whites turn.
	 * @return if it is whites turn.
	 */
	public boolean isWhite() {
		return isWhite;
	}

	/**
	 * Updates board with the given Move.
	 * Precondition: Is a legal move
	 * @return true if a promotion should be done
	 */
	public boolean move(Move move) {
		Piece from = placement[move.from()];
		Piece to = placement[move.to()];
		if(to != null && to.type() == 'K') 
			gameOver = true;
		//Change pawn movement after first move
		if(from.type() == 'p')
			from.setType('P');
			//Promotion
		from.placement = move.to();
		placement[move.to()] = placement[move.from()];
		placement[move.from()] = null;
		isWhite = !isWhite;
		
		if(placement[move.to()].type() == 'P' && (placement[move.to()].isWhite() && move.to() < 8  || !placement[move.to()].isWhite() && 56 <= move.to()))
			return true;
		return false;
	}

	/**
	 * Creates a copy of this placement array
	 * @return the placement array copy
	 */
	public Piece[] get() {
		Piece[] copy = new Piece[64];
		for(int i = 0; i < 64; i++)
			copy[i] = (placement[i] == null) ? null : placement[i].copy();
		return copy;
	}

	/**
	 * Returns true if the given move is legal.
	 * @param move Move to be checked.
	 * @return Boolean, true if the move is legal and vice versa.
	 */
	public boolean isLegal(Move move) {
		// Has to move a piece.
		if(move.to() == move.from()) return false;
		
		Piece from = placement[move.from()];
		//Not a valid piece
		if(from == null) return false;

		// The choosen piece is not the correct color.
		if(from.isWhite() != isWhite) return false;
		// The move is not inside the board.
		if(move.from() < 0 || move.from() > 64 || move.to() < 0 || move.to() > 64) return false;

		// The piece is moved to non empty space or empty space is selected.
		if(placement[move.to()] != null && placement[move.to()].isWhite() == from.isWhite()) return false;
		//A valid move for the piece
		if(!(from.isValid(move.to()%8 - move.from()%8, move.to()/8 - move.from()/8))) return false;
		//Pawn move is an attack if diagonal
		int change = Math.abs(move.from() - move.to());
		if(from.type() == 'P' || from.type() == 'p')
			if(change == 9 || change == 7){
				if(placement[move.to()] == null || placement[move.to()].isWhite() == isWhite)
					return false;
			}
			//Forward movement whihc must be empty
			else {
				if(placement[move.to()] != null)
					return false;
			}
		//Gets the direction of the change if there is a change
		int rowChange = (0 < Math.abs(move.from()/8 - move.to()/8)) ? (move.from()/8 - move.to()/8)/Math.abs(move.from()/8 - move.to()/8) : 0;
		int coloumnChange = (0 < Math.abs(move.from()%8 - move.to()%8)) ? (move.from()%8 - move.to()%8)/Math.abs(move.from()%8 - move.to()%8) : 0;

		//Checks all places between the movements start and end and check if they are empty except if the peice is a knight
		boolean legal = true;
		int moveChange = 8*rowChange + coloumnChange;
		int i = move.from() - moveChange;
		while(from.type() != 'N' && legal && i != move.to()) {
			if(placement[i] != null)
				legal = false;
			i = i - moveChange;
		}
		
		return legal;
	}

	/**
	 * Finds all legal moves with the current board state considering current color and pieces 
	 * @return Returns a list of legal moves based on the current board state.
	 */
	public Move[] legalMoves() {
		Move[] buffer = new Move[512];
		int counter = 0;
		Piece[] playerPieces = (isWhite) ? white : black;
		for(Piece from:playerPieces)
			for(int to = 0; to < 64; to++) {
				Move move = new Move(from.placement, to);
				if(isLegal(move)) {
					buffer[counter] = move;
					counter++;
				}
			}
		//Correct sized array.
		Move[] legalMoves = new Move[counter];
		for(int i = 0; i < counter; i++)
			legalMoves[i] = buffer[i];
		return legalMoves;
	}
	
	/**
	 * Check if any legal moves are availible
	 * @return true if there is a legal move possible for the current color
	 */
	public boolean anyLegalMove() {
		Piece[] playerPieces = (isWhite) ? white : black;
		for(Piece from:playerPieces)
			for(int to = 0; to < 64; to++) {
				Move move = new Move(from.placement, to);
					if(isLegal(move)) {
						return true;
					}
				}
		return false;
	}
	
	/**
	 * Change the type of piece for promotions
	 */
	public void changeType(int placement, char type) {
		this.placement[placement].setType(type);
	}

	/**
	 * States if there are anymore legal moves and then decides if the game is over.
	 * @return true if no more legal moves or no more pieces in a color.
	 */
	public boolean isGameOver() {
		if(!anyLegalMove()) gameOver = true;
		return (gameOver);
	}

	/**
	 * Creates a copy of this board.
	 * @return A board in the same state, with pieces at the same places and same color turn.
	 */
	public Board copy() {
		return (new Board(white, black, isWhite));
	}

	/** 
	 * Checks if this board is equal to the given board.
	 * @param Object to be compared to.
	 * @return if the object is equal to this board.
	 */
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(!(other instanceof Board))
			return false;
		Board board = (Board) other;
		int i = 0;
		while(this.placement[i] != null && board.placement[i] != null 
			&& this.placement[i].type() == board.placement[i].type() 
			&& this.placement[i].isWhite() == board.placement[i].isWhite()
			&& i<64)
			i++;
		return (i == 64 && this.isWhite == board.isWhite);
	}
	
	/**
	 * Creates a hashcode representing this board.
	 * @return a code representing this board in its current state.
	 */
	public int hashCode() {
		int hash = isWhite ? 31 : 0;
		for(int i = 1 ; i < 64;i++)
			if(placement[i] != null && placement[i].isWhite())
				hash = hash + i * 31;
			else if(placement[i] != null)
				hash = hash - i * 31;
		return hash;
	}

	
}
