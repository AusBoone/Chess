import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * ChessBoard class represents a chess board and all the chess pieces on it.
 * The class provides methods for moving pieces, checking the game status (check, checkmate, stalemate), 
 * and handling pawn promotion.
 */
public class ChessBoard {
    private ChessPiece[][] board;
    private ArrayList<ChessPiece> whitePieces;
    private ArrayList<ChessPiece> blackPieces;
    private boolean whiteTurn;

    // constructor of the ChessBoard class
    public ChessBoard() {
    // Create a 2D array to represent the board
    board = new ChessPiece[8][8];
    // Create an ArrayList to store the white pieces
    whitePieces = new ArrayList<>();
    // Create an ArrayList to store the black pieces
    blackPieces = new ArrayList<>();
    // Set whiteTurn to true, indicating that it's the white player's turn
    whiteTurn = true;
    // initialize the board with the chess pieces
    initializeBoard();
    }

    /**
     * Initialize the chess board with the chess pieces in their starting positions,
     * initialize the board and add the pieces to the board
     */
    private void initializeBoard() {
        // initilaize the pawns
        for (int i = 0; i < 8; i++) {
            whitePieces.add(new Pawn(this, true, i, 6));
            blackPieces.add(new Pawn(this, false, i, 1));
        }
        // initialize the white back row pieces
        whitePieces.add(new Rook(this, true, 0, 7));
        whitePieces.add(new Rook(this, true, 7, 7));
        whitePieces.add(new Knight(this, true, 1, 7));
        whitePieces.add(new Knight(this, true, 6, 7));
        whitePieces.add(new Bishop(this, true, 2, 7));
        whitePieces.add(new Bishop(this, true, 5, 7));
        whitePieces.add(new Queen(this, true, 3, 7));
        whitePieces.add(new King(this, true, 4, 7));
        // initialize the black back row pieces
        blackPieces.add(new Rook(this, false, 0, 0));
        blackPieces.add(new Rook(this, false, 7, 0));
        blackPieces.add(new Knight(this, false, 1, 0));
        blackPieces.add(new Knight(this, false, 6, 0));
        blackPieces.add(new Bishop(this, false, 2, 0));
        blackPieces.add(new Bishop(this, false, 5, 0));
        blackPieces.add(new Queen(this, false, 3, 0));
        blackPieces.add(new King(this, false, 4, 0));

        for (ChessPiece piece : whitePieces) {
            // add the white pieces to the board
            board[piece.getX()][piece.getY()] = piece;
        }
        for (ChessPiece piece : blackPieces) {
            // add the black pieces to the board
            board[piece.getX()][piece.getY()] = piece;
        }
    }
  
    /**
     * Return the chess piece at the specified position
     * @param x the x-coordinate of the position
     * @param y the y-coordinate of the position
     * @return the chess piece at the specified position, or null if there is no piece
     */
    public ChessPiece getPieceAt(int x, int y) {
        // return the chess piece at the specified position
        return board[x][y];
    }

    /**
     * Move a chess piece from one location to another
     * @param x1 the x-coordinate of the starting position
     * @param y1 the y-coordinate of the starting position
     * @param x2 the x-coordinate of the ending position
     * @param y2 the y-coordinate of the ending position
     * @return true if the move is successful, false otherwise
     */
    public boolean movePiece(int x1, int y1, int x2, int y2) {
        //get the chess piece at the starting position
        ChessPiece piece = getPieceAt(x1, y1);
        // return false if there is no piece at the starting position
        if (piece == null) {
            return false;
        }
        // return false if the player is trying to move the opponent's piece
        if (whiteTurn != piece.isWhite()) {
            return false;
        }
        // check if the piece can move to the ending coordinates
        if (!piece.canMove(x2, y2)) {
            return false;
        }
        // check if there is a piece at the ending position
        ChessPiece capturedPiece = getPieceAt(x2, y2);
        // remove the captured piece from the list of pieces
        if (capturedPiece != null) {
            if (capturedPiece.isWhite()) {
                whitePieces.remove(capturedPiece);
            } else {
                blackPieces.remove(capturedPiece);
            }
        }
        // update the chess board
        board[x1][y1] = null;
        board[x2][y2] = piece;
        piece.setX(x2);
        piece.setY(y2);
        // switch the turn
        whiteTurn = !whiteTurn;
        return true;
    }

    /**
     * Method to check if the game is in checkmate state
     * @return true if the game is in checkmate, false otherwise
     */
    public boolean isCheckmate() {
        // list of opponent's pieces
        ArrayList<ChessPiece> pieces;
        ChessPiece king;
        // check whose turn it is
        if (whiteTurn) {
            pieces = blackPieces;
            king = findKing(whitePieces);
        } else {
            pieces = whitePieces;
            king = findKing(blackPieces);
        }
        // check if any of the opponent's pieces can move to the king's position
        for (ChessPiece piece : pieces) {
            if (piece.canMove(king.getX(), king.getY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to check if the king of the current player is in check
     * @param whiteTurn current player's turn
     * @return true if the king is in check, false otherwise
     */
    public boolean isCheck(boolean whiteTurn) {
        ArrayList<ChessPiece> opponentPieces = whiteTurn ? blackPieces : whitePieces;
        ChessPiece king = findKing(whiteTurn ? whitePieces : blackPieces);
        // check if any of the opponent's pieces can move to the king's position
        for (ChessPiece piece : opponentPieces) {
            if (piece.canMove(king.getX(), king.getY())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to find the king in the pieces list
     * @param pieces list of pieces
     * @return the king piece
     */
    private ChessPiece findKing(ArrayList<ChessPiece> pieces) {
        for (ChessPiece piece : pieces) {
            if (piece instanceof King) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Method to check if the game is in stalemate state
     * @return true if the game is in stalemate, false otherwise
     */
    public boolean isStalemate() {
        ArrayList<ChessPiece> pieces;
        // check whose turn it is
        if (whiteTurn) {
            pieces = whitePieces;
        } else {
            pieces = blackPieces;
        }
        // check if any legal move can be made by the current player
        for (ChessPiece piece : pieces) {
            ArrayList<Integer[]> moves = piece.getMoves();
            for (Integer[] move : moves) {
                int x = move[0], y = move[1];
                if (isLegalMove(piece, x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to check if the game is in check or checkmate state
     * @return 1 if the game is in check state, 2 if the game is in checkmate state, 0 otherwise
     */
    public int checkCheckMate() {
        ChessPiece king = findKing(whiteTurn ? whitePieces : blackPieces);
        if (isCheck(whiteTurn)) {
            if (isCheckmate()) {
                return 2;
            } else {
                return 1;
            }
        }
        return 0;
    }

    /**
     * Method to handle the pawn promotion
     * @param pawn the pawn to be promoted
     * @param x the target x coordinate
     * @param y the target y coordinate
     */
    public void handlePawnPromotion(Pawn pawn, int x, int y) {
        // check if the pawn is at the opposite side of the board
        if ((pawn.isWhite() && y == 7) || (!pawn.isWhite() && y == 0)) {
            // remove the pawn from the board
            removePiece(pawn.getX(), pawn.getY());
            // prompt the user to choose a piece to promote the pawn to
            ChessPiece newPiece = promptForPromotion();
            // add the new piece to the board
            addPiece(newPiece, x, y);
        }
    }

    /**
     * Method to check if a move is legal
     * @param x1 the current x coordinate of the piece
     * @param y1 the current y coordinate of the piece
     * @param x2 the target x coordinate
     * @param y2 the target y coordinate
     * @return true if the move is legal, false otherwise
     */
/**
    public boolean isLegalMove(int x1, int y1, int x2, int y2) {
        ChessPiece piece = board[x1][y1];
        // check if the piece is not null
        if (piece == null) {
            return false;
        }
        // check if the piece is the correct color
        if (whiteTurn != piece.isWhite()) {
            return false;
        }
        // check if the piece can move to the target position
        if (!piece.canMove(x2, y2)) {
            return false;
        }
        // check if the move puts the king of the current player in check
        movePiece(x1, y1, x2, y2);
        if (isCheck(whiteTurn)) {
            movePiece(x2, y2, x1, y1);
            return false;
        }
        movePiece(x2, y2, x1, y1);
        return true;
    }
*/

    /**
     * Method to check if a move is legal or not
     * @param piece the piece to be moved
     * @param x the target x coordinate
     * @param y the target y coordinate
     * @return true if the move is legal, false otherwise
     */
    public boolean isLegalMove(ChessPiece piece, int x, int y) {
        // check if the piece can move to the target position
        if (!piece.canMove(x, y)) {
            return false;
        }
        // check if the move puts the king of the current player to check
        ChessPiece king = findKing(whiteTurn ? whitePieces : blackPieces);
        movePiece(piece.getX(), piece.getY(), x, y);
        if (isCheck(whiteTurn)) {
            movePiece(x, y, piece.getX(), piece.getY());
            return false;
        }
        movePiece(x, y, piece.getX(), piece.getY());
        return true;
    }

    /**
    * Method to prompt the user to choose a piece to promote the pawn to
    * @return the new piece the user selected
    */
    private ChessPiece promptForPromotion() {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null, "Choose a piece to promote the pawn to",
                "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        switch(choice) {
            case 0:
                return new Queen(pawn.isWhite(), x, y);
            case 1:
                return new Rook(pawn.isWhite(), x, y);
            case 2:
                return new Bishop(pawn.isWhite(), x, y);
            case 3:
                return new Knight(pawn.isWhite(), x, y);
            default:
                return null;
        }
    }

    /**
     * Method to prompt the user to choose a piece to promote the pawn to
     * @return the new piece the user selected
     */
/** 
    public ChessPiece promptForPromotion() {
        // create a JOptionPane to display the options for promotion
        Object[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null, "Choose a piece to promote the pawn to:",
                "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        // return the chosen piece
        switch (choice) {
            case 0:
                return new Queen(whiteTurn);
            case 1:
                return new Rook(whiteTurn);
            case 2:
                return new Bishop(whiteTurn);
            case 3:
                return new Knight(whiteTurn);
            default:
                return null;
        }
    }
*/

    /**
     * This class is responsible for validating the moves and ensuring that the game follows the chess rules.
     */
    public class MoveValidation {
    /**
     * Method to validate a move
     * @param x1 the current x coordinate of the piece
     * @param y1 the current y coordinate of the piece
     * @param x2 the target x coordinate
     * @param y2 the target y coordinate
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(int x1, int y1, int x2, int y2) {
        ChessPiece piece = board[x1][y1];
        // check if the piece is not null
        if (piece == null) {
            return false;
        }
        // check if the piece is the correct color
        if (whiteTurn != piece.isWhite()) {
            return false;
        }
        // check if the piece can move to the target position
        if (!piece.canMove(x2, y2)) {
            return false;
        }
        // check if the move puts the king of the current player in check
        movePiece(x1, y1, x2, y2);
        if (isCheck(whiteTurn)) {
            movePiece(x2, y2, x1, y1);
            return false;
        }
        movePiece(x2, y2, x1, y1);
        return true;
    }
  }
    
}
