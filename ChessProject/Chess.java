import java.util.Scanner;

public class Chess {
    private static final String newline = System.lineSeparator();
    private static Scanner scanner = new Scanner(System.in);
    private static Board board = new Board();
    
    private static int turn = 1; 
    private static boolean whiteCPU;
    private static boolean blackCPU;

    /**
     * Start a game of chess and make turns until game is finished
     */
    public static void main(String[] args) {
        whiteCPU = initPlayer("white");
	    blackCPU = initPlayer("black");

        while(!board.isGameOver()){
	    drawBoard();
	    boolean isWhite = turn % 2 == 1;
            if(isWhite)
                System.out.println("White Turn");
            else 
                System.out.println("Black Turn");
            
            if((isWhite && whiteCPU)  || (!isWhite  && blackCPU))
                CPUTurn(isWhite);
            else
                nextTurn(isWhite);
       }
       drawBoard();
       //Check which kings are left
        boolean[] kingsLeft = new boolean[2];
        Piece[] pieces = board.get();
        for(int i = 0; i < 64; i++)
            if(pieces[i] != null && pieces[i].type() == 'K')
                if(pieces[i].isWhite())
                    kingsLeft[0] = true;
                else
                    kingsLeft[1] = true;

        if(kingsLeft[0] && kingsLeft[1])
            System.out.println("Stalemate");
         else if (kingsLeft[0])
            System.out.println("White player has won in " + turn + " turns.");
         else if (board.legalMoves().length == 0)
         System.out.println("Black player has won in " + turn + " turns.");
        
        
    }
    /**
     * Prompts user if the given player should be a CPU.
     * @param piece color of the prompted piece
     * @return true if the given color shoud be CPU
     */
    private static boolean initPlayer(String piece){
        System.out.println("Is " + piece + " CPU?" + newline + "0 = no" + newline + "1 = yes");
        int choice = recieveInt();
        while(choice < 0 || 1 < choice) {
            System.out.println("Invalid input, try again." + newline + "0 = no" + newline + "1 = yes");
            choice = recieveInt();
        }
        return choice == 1;
    }

    /**4
     * Draws the board with black pieces, white pieces and the int array which contains board spaces and will be numerated according to index + 1
     * @param moves Array containing board spaces. Precon. Must be valid board spaces, without a piece on
     */
    private static void drawBoard() {

    // Generates a char array with 5 length, then replaces all null characters with the string "|---". Effectively repeating the string 5 times.
    String columnDashes = new String(new char[8]).replace("\0", "|---");
    Piece[] pieces = board.get();
	System.out.println("---"+columnDashes+"|");
    String blackColor = "\u001B[31m";
    String defaultColor = "\u001B[37m";
	for(int i = 0; i<8; i++) {
		System.out.print(8-i + "  ");
		for(int j = 0; j<8; j++){
			String charachterChar = " ";
			if(pieces[i*8+j] != null) {
				String pieceColor = (pieces[i*8+j].isWhite()) ? "" : blackColor;
                charachterChar = pieceColor + pieces[i*8+j].type() + defaultColor;
            }
			System.out.print("| "+charachterChar+" ");
		}
		System.out.println("|"+newline+"---"+columnDashes+"|");
	}
    
    String columnSpaces = new String(new char[8]).replace("\0", "|   ");

	System.out.println("   "+columnSpaces+"|");
	System.out.println("   | a | b | c | d | e | f | g | h");	
    }	

 
    /**
     * Generates a move for CPU player, and then proceeds to complete that move.
     */
    private static void CPUTurn(boolean isWhite){
        MinimaxTree tree = new MinimaxTree(board, 3, isWhite);
        Move move = tree.next();
        if(board.move(move))
            board.changeType(move.to(), 'Q');
        turn++;
    }
    /*
     * Decides which player turn it is, presents the players moves and registers player input. 
     */
    private static void nextTurn(boolean isWhite){
        String start;
        int destination;
        boolean validPiece = true;
        int coordinate;
        Piece[] pieces = board.get();
        
        System.out.println("Please select which piece to move:");
        do{
            start = scanner.nextLine().trim();
            if(start.length() != 2)
                System.out.println("Invalid input, try again.");
            
        } 
        while(start.length() != 2);
        
        coordinate = getCoordinate(start);
       	if(pieces[coordinate] == null || pieces[coordinate].isWhite() != isWhite)
           validPiece = false;

        if(validPiece){
            System.out.println("Please select where to move");
            String end;
            do{
                end = scanner.nextLine().trim();
                if(end.length() != 2)
                    System.out.println("Invalid input, try again.");
                
            }
            while(end.length() != 2 && getCoordinate(end) > 0 && getCoordinate(end) < 64 );
            if(!end.equals("0")){
                destination = getCoordinate(end);
                Move move = new Move(coordinate, destination);
                if(destination < 64 && board.isLegal(move)) {
                        if(board.move(move)) {
                            System.out.println("What do you want to promote the piece into? (Q, K, B, N, R, P)");
                            String promo = scanner.nextLine();
                            String pieceChars = "QKBNRP";
                            while(pieceChars.contains(promo.substring(1))){
                                System.out.println("Invalid input");
                                promo = scanner.nextLine();
                            }
                            board.changeType(destination, promo.charAt(0 ));
                        }
                            
                        turn = turn + 1;
                }
                else
                    System.out.println("Not a legal destination");
            }
        } 
	    else
            System.out.println("That is not one of your pieces...");

    } 
    /**
     * @param input A string in algebraic notation.. (A1, B3, C5...) Limits are A-E and 1-5.
     * @return Returns an integer correlating to the given coordinate's index in the board array. If the input is out of bounds, the output is -1.
     */
    private static int getCoordinate(String input) {
        // assume input is in correct algebraic notation ( b1, c2, d5 etc. )
        int y = ((int) input.toUpperCase().charAt(0)) - 64;
        int x = 8 - Character.getNumericValue(input.charAt(1));
        
        // either input is invalid ((fucked)), try flipping it
        if(y < 0 || y > 8 || x < 0 || x > 8) {
            y = ((int) input.toUpperCase().charAt(1)) - 64;
            x = 8 - Character.getNumericValue(input.charAt(0));
        }
        int result = (x*8)+y;
        // if any of the coordinates are out of bounds, it will be treated as an invalid coordinate
        if(x < 0 || x > 8 || y < 1 || y > 8)
            result  = -1;
        
        return result-1;
    }
    /**
     * Gets input from user and check if it is an int
     * @return Returns a valid int from user
     */
    private static int recieveInt() {
        String input = scanner.nextLine();
        while(input.length() <= 0 || input.charAt(0) < 47 || 58 < input.charAt(0)) {
            System.out.println("Invalid number try again");
            input = scanner.nextLine();
        }
        return (int) input.charAt(0) - 48;
    }
}