import path.to.ChessBoard;
import path.to.ComputerPlayer;

/**
 * Main class that runs the chess game.
 * Initializes the chess board, move validation, computer player, and game loop.
 * Game loop prompts user for move, validates move, makes move on the board,
 * checks for checkmate or stalemate, and switches turns.
 * @param args command line arguments
 */
public static void main(String[] args) {
    ChessBoard chessBoard = new ChessBoard();
    MoveValidation moveValidation = chessBoard.new MoveValidation();
    boolean whiteTurn = true;
    ComputerPlayer computerPlayer = new ComputerPlayer(chessBoard, false);
    while (true) {
        // display the chess board
        chessBoard.display();
        if (whiteTurn) {
            // prompt the user for a move
            int x1, y1, x2, y2;
            // get the move from user
            // validate the move
            if (!moveValidation.isValidMove(x1, y1, x2, y2)) {
                System.out.println("Invalid move, try again");
                continue;
            }
            // make the move
            chessBoard.movePiece(x1, y1, x2, y2);
        } else {
            computerPlayer.makeMove();
        }
        // check for checkmate or stalemate
        if (chessBoard.isCheckmate(whiteTurn)) {
            System.out.println("Checkmate! " + (whiteTurn ? "White" : "Black") + " wins!");
            break;
        } else if (chessBoard.isStalemate(whiteTurn)) {
            System.out.println("Stalemate! The game is a draw.");
            break;
        }
        // switch the turn
        whiteTurn = !whiteTurn;
    }
}
