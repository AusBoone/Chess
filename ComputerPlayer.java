import path.to.ChessBoard;

/**
 * ComputerPlayer class represents a computer player in the chess game.
 * The class provides a method for the computer to make a move.
 */
public class ComputerPlayer {
    // reference to the chess board
    private ChessBoard chessBoard;
    // color of the computer player
    private boolean isWhite;

    /**
     * Constructor for the ComputerPlayer class
     * @param chessBoard reference to the chess board
     * @param isWhite color of the computer player
     */
    public ComputerPlayer(ChessBoard chessBoard, boolean isWhite) {
        // set the chessBoard variable to the given chessBoard
        this.chessBoard = chessBoard;
        // set the isWhite variable to the given isWhite
        this.isWhite = isWhite;
    }

    /**
     * Makes a move for the computer player.
     * Generates random coordinates for the move and check if the move is valid.
     * If the move is valid, it makes the move on the chess board.
     */
    public void makeMove() {
        // generate random x1, y1, x2, y2
        int x1 = (int)(Math.random() * 8);
        int y1 = (int)(Math.random() * 8);
        int x2 = (int)(Math.random() * 8);
        int y2 = (int)(Math.random() * 8);
        // check if the move is valid
        MoveValidation moveValidation = chessBoard.new MoveValidation();
        if (!moveValidation.isValidMove(x1, y1, x2, y2)) {
            makeMove();
            return;
        }
        // make the move
        chessBoard.movePiece(x1, y1, x2, y2);
    }
}
