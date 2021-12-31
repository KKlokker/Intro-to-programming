public class Piece {
    public int placement;
    private char type;
    private Movement movement;
    private boolean isWhite;

    /*
     * The peice chars are K,Q,R,N,B,P,p
     * K: King
     * Q: Queen
     * R: Rook
     * N: Knight
     * B: Bishop
     * P: Pawn
     * p: starter pawn
    */

    /**
     * Construct a new piece.
     * @param placement The placement of the piece on the board, must be 0 <= placement < 64
     * @param type The type of piece. Must be a one of the following valid chars: K,Q,R,N,B,P,p
     * @param isWhite If the piece is a white piece
    */
    public Piece(int placement, char type, boolean isWhite) {
        this.placement = placement;
        this.type = type;
        this.isWhite = isWhite;
        this.movement = getMovement(type);
    }

    /**
     * Returns the movement of the piece.
     * @param type what type of piece it is
     * @return A Movement object describing the movement of the object
     */
    private Movement getMovement(char type) {
        Movement moves;
        switch(type) {
            case 'K':
                moves = new Movement(false, false, false,isWhite , new boolean[][]{{true,true},{false,true}});
                break;
            case 'Q':
                moves = new Movement(true, true, true,isWhite , new boolean[][]{{true}});
                break;
            case 'R':
                moves = new Movement(true, true, false,isWhite , new boolean[][]{{true}});
                break;
            case 'N':
                moves = new Movement(false, false, false,isWhite , new boolean[][]{{false,true,false},{false,false,true},{false,false,false}});
                break;
            case 'B':
                moves = new Movement(false, false, true,isWhite , new boolean[][]{{true}});
                break;
            case 'p':
                moves = new Movement(false, false, false,isWhite , new boolean[][]{{false, true}});
                break;
            case 'P':
                moves = new Movement(false, false, false,isWhite , new boolean[][]{{false, false, false}});
                    break;
            default:
                moves = new Movement(false, false, false,isWhite , new boolean[][]{{true}});
                break;
        }
        return moves;
    }

    /**
     * Check if a move is valid, where the move is relative to the piece
     * @param x the movement in the x axis
     * @param y the movement in the y axis
     * @return true if the move is valid for the peice
     */
    public boolean isValid(int x, int y)  {
        if(movement.moves.length == 1 && movement.moves[0].length == 2) //The starter pawn
            return movement.pawnValid(x, y);
        if(movement.moves.length == 1 && movement.moves[0].length == 3) //The normal pawn
            return movement.PawnValid(x, y);
        else
            return movement.pieceValid(Math.abs(x), Math.abs(y)); // Another piece
    }

    /**
     * Getter for if this piece is white
     * @return true if this piece is white
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * Getter for the type of this piece
     * @return the type of piece according the reference in document
     */
    public char type() {
        return type;
    }

    /**
     * Setter for the type of this piece
     * @param type a type for the piece must be valid according the document reference
     */
    public void setType(char type) {
        this.type = type;
        this.movement = getMovement(type);
    }

    /**
     * Copies this piece
     * @return a copy of this piece
     */
    public Piece copy() {
        Piece copy = new Piece(placement, type, isWhite);
        return copy;
    }

    /**
     * Class for describing the movement of a piece
     */
    private class Movement {
        boolean horizontal, vertical, diagonal, isWhite;
        boolean[][] moves;
    
        /**
         * Constructs a movement object
         * @param horizontal If the piece is allowed to move horizontally
         * @param vertical If the piece is allowed to move vertically
         * @param diagonal If the piece is allowed to move diagonally
         * @param isWhite If the piece is white
         * @param moves A boolean matrice for valid movement, where the piece is at the down left corner and the matrice is assumed to be symmetric on all 4 rotations 
         */
        private Movement(boolean horizontal, boolean vertical, boolean diagonal, boolean isWhite, boolean[][] moves){
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.diagonal = diagonal;
            this.moves = moves;
            this.isWhite = isWhite;
        }
    
        /**
         * Returns if a piece which is not pawn is can do the relative move
         * @param x the relative movement on the x axis
         * @param y the relative movement on the y axis
         * @return if the piece can peform the given movement
         */
        private boolean pieceValid(int x, int y) {
            int trueY = moves.length -1 -y;
            if(x == 0 && y == 0) return false;
            if(vertical && x == 0) return true;
            if(horizontal && trueY == 0) return true;
            if(diagonal && x==y) return true;
            if(x < moves.length && y < moves.length && moves[trueY][x]) return true;
            return false;
        }
        
        /**
         * Returns if the movement is valid for a starter pawn
         * @param x the relative movement on the x axis
         * @param y the relative movement on the y axis
         * @return if the piece can peform the given movement
         */
        private boolean pawnValid(int x, int y) {
            int trueY = (isWhite) ? -y : y;
            return ((trueY == 1 && (x == 1 || x == -1 || x == 0)) || (trueY == 2 && x == 0));
        }

        /**
         * Returns if the movement is valid for a normal pawn
         * @param x the relative movement on the x axis
         * @param y the relative movement on the y axis
         * @return if the piece can peform the given movement
         */
        private boolean PawnValid(int x, int y) {
            int trueY = (isWhite) ? -y : y;
            return (trueY == 1 && (x == 1 || x == -1 || x == 0));
        }
    }
    
}
