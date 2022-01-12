/**
 * MinimaxTree
 */
public class MinimaxTree {
	private Board board;
	private MinimaxTree[] nodes;
	private boolean isWhite;

	/**
  	* Construct this tree
  	* @param board The board which the tree is created from
  	* @param depth The depth of the tree where 0 is no children
 	* @param isWhite The color of the current player
 	*/
	public MinimaxTree(Board board, int depth, boolean isWhite) {
		this.board = board;
		this.isWhite = isWhite;
		nodes = new MinimaxTree[0];
		if(depth>0) {
			Move[] moves = board.legalMoves();
			nodes = new MinimaxTree[moves.length];
			for(int i = 0; i < moves.length; i++) {
				Board copy = board.copy();
				copy.move(moves[i]);
				MinimaxTree node = new MinimaxTree(copy, depth-1, isWhite);
				nodes[i] = node;
			}
		}
	}

	/**
	 * Gives a value of the board according to values from
	 * https://www.chess.com/terms/chess-piece-value
	 * @param board the board which should be valued
	 * @param isWhite from which color the board should be scored
	 * @return a score of the board
	 */
	private static int heuristic(Board board, boolean isWhite) {
		int boardScore = 0;
		Piece[] pieces = board.get();
		for(int i = 0; i < pieces.length; i++) {
			int score = 0;
			char type;
			if (pieces[i] != null)  {
				type = pieces[i].type();
				if(type == 'P' || type == 'p')
					score = 1;
				else if(type == 'N' || type == 'B')
					score = 3;
				else if(type == 'R')
					score = 5;
				else if(type == 'Q')
					score = 9;
				else if(type == 'K')
					score = 1000;
				if(pieces[i].isWhite() != isWhite)
					score = score * -2; 
			}
			boardScore = boardScore + score;
		}
		return boardScore;
	}

	/**
	 * @return the best move of the tree by choosing the best child outcome
	 */
	public Move next() {
		int score = Integer.MIN_VALUE;
		int index = 0;
		for(int i = 0; i < nodes.length; i++)
			if(score < nodes[i].nodeScore()) {
				score = nodes[i].nodeScore();
				index = i; 
			}
		return board.legalMoves()[index];
	}	

	/**
	 * @return The best score of the node and its children
	 */
	private int nodeScore() {
		if(nodes.length == 0)
			return heuristic(board,isWhite);
		int score = 0;
		for(int i = 0; i < nodes.length; i++) {
			int childScore = heuristic(nodes[i].board, isWhite);
			score = score + childScore;
		}
		score = score / nodes.length;
		return score;
	}

}
