/**
 * MinimaxTree
 */
public class MinimaxTree {
	private Board board;
	private nodeList nodes;
	private boolean isWhite;

	/**
	 * Creates a tree with childs which is possible boards from the current board
	 * @param board The board the trees root is at
	 * @param depth The deoth of the tree 
	 * @param isWhite if the tress root is white
	 */
	public MinimaxTree(Board board, int depth, boolean isWhite) {
		this.board = board;
		this.isWhite = isWhite;
		nodes = new nodeList();
		if(depth>0)
			addNodes(this, depth);
	}

	/**
	 * Creates children to a root from all possible moves
	 * @param tree The root whihc children is added to
	 * @param depth The remaining depth of the tree
	 */
	private void addNodes(MinimaxTree tree, int depth) {
		Move[] moves = tree.board.legalMoves();
		for(Move move: moves) {
			Board copy = tree.board.copy();
			if(copy.move(move))
				copy.changeType(move.to(), 'Q');
			MinimaxTree node = new MinimaxTree(copy, depth-1, isWhite);
			tree.nodes.add(node);
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
		for(int i = 0; i < nodes.length(); i++)
			if(score < nodes.At(i).nodeScore()) {
				score = nodes.At(i).nodeScore();
				index = i; 
			}
		return board.legalMoves()[index];
	}	

	/**
	 * @return The best score of the node and its children
	 */
	private int nodeScore() {
		if(nodes.length() == 0)
			return heuristic(board,isWhite);
		int score = heuristic(nodes.At(0).board, isWhite);
		for(int i = 1; i < nodes.length(); i++) {
			int childScore = heuristic(nodes.At(i).board, isWhite);
			score = (score < childScore) ? childScore : score;
		}
		return score;
	}

	/**
	 * Class for the list of children to a roow
	 */
	private class nodeList {
		private int index;
		private MinimaxTree[] nodes;

		/**
		 * Creates a list of trees with length 10
		 */
		private nodeList() {
			index = 0;
			nodes = new MinimaxTree[10];
		}

		/**
		 * Adds an element to this list
		 * @param node The node which should be added to the list
		 */
		private void add(MinimaxTree node) {
			if(index == nodes.length)
				expand();
			nodes[index] = node;
			index++;
		}

		/**
		 * Expands the list to the double length
		 */
		private void expand() {
			MinimaxTree[] newNodes = new MinimaxTree[nodes.length * 2];
			for(int i = 0; i < index; i++)
				newNodes[i] = nodes[i];
			nodes = newNodes;
		}

		/**
		 * @return the length of the list
		 */
		public int length() {
			return index;
		}

		/**
		 * @param the index of the wanted node
		 * @return the node at index i
		 */
		public MinimaxTree At(int i) {
			return nodes[i];
		}

	}
}
