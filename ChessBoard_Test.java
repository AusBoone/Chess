import org.junit.Test;
import static org.junit.Assert.*;
import packageName.ChessBoard;

/**
 * ChessBoardTest is a JUnit test class for the ChessBoard class.
 * This class tests the functionality of the ChessBoard class.
 */
public class ChessBoardTest {

    /**
     * Test method to check if the chess board has been created correctly
     * and all the pieces are in their correct starting positions
     */
    @Test
    public void testBoardCreation() {
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece[][] board = chessBoard.getBoard();
        // Check that the board is 8x8
        assertEquals(8, board.length);
        assertEquals(8, board[0].length);
        // Check that all the pieces are in their starting positions
        for (int i = 0; i < 8; i++) {
            assertTrue(board[i][6] instanceof Pawn);
            assertTrue(board[i][1] instanceof Pawn);
        }
        assertTrue(board[0][7] instanceof Rook);
        assertTrue(board[7][7] instanceof Rook);
        assertTrue(board[1][7] instanceof Knight);
        assertTrue(board[6][7] instanceof Knight);
        assertTrue(board[2][7] instanceof Bishop);
        assertTrue(board[5][7] instanceof Bishop);
        assertTrue(board[3][7] instanceof Queen);
        assertTrue(board[4][7] instanceof King);
        assertTrue(board[0][0] instanceof Rook);
        assertTrue(board[7][0] instanceof Rook);
        assertTrue(board[1][0] instanceof Knight);
        assertTrue(board[6][0] instanceof Knight);
        assertTrue(board[2][0] instanceof Bishop);
        assertTrue(board[5][0] instanceof Bishop);
        assertTrue(board[3][0] instanceof Queen);
        assertTrue(board[4][0] instanceof King);
    }

    /**
     * Test the getPieceAt method to ensure it correctly returns 
     * the piece at the specified coordinates
     */
    @Test
    public void testGetPieceAt() {
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece piece = chessBoard.getPieceAt(0,0);
        assertTrue(piece instanceof Rook);
        assertFalse(piece.isWhite());
        piece = chessBoard.getPieceAt(4,7);
        assertTrue(piece instanceof King);
        assertTrue(piece.isWhite());
        piece = chessBoard.getPieceAt(3,1);
        assertTrue(piece instanceof Pawn);
        assertFalse(piece.isWhite());
    }

    /**
     * Test the movePiece method
     */
    @Test
    public void testMovePiece() {
        ChessBoard chessBoard = new ChessBoard();
        // Test moving a piece to an empty space
        assertTrue(chessBoard.movePiece(1, 1, 2, 2));
        // Test moving a piece to capture an opponent's piece
        assertTrue(chessBoard.movePiece(6, 1, 5, 2));
        // Test trying to move an opponent's piece
        assertFalse(chessBoard.movePiece(0, 0, 1, 0));
        // Test trying to move to an invalid location
        assertFalse(chessBoard.movePiece(1, 0, 2, 3));
    }

    /**
     * Test the isCheckmate method
     */
    @Test
    public void testIsCheckmate() {
        ChessBoard chessBoard = new ChessBoard();
        // move the black pawn to put white king in check
        chessBoard.movePiece(1, 1, 3, 2); 
        // white king should be in checkmate
        assertTrue(chessBoard.isCheckmate()); 
        // move the black queen to put white king in checkmate
        chessBoard.movePiece(4, 0, 3, 1); 
        // white king should be in checkmate
        assertTrue(chessBoard.isCheckmate()); 
        // move the black queen back to its original position
        chessBoard.movePiece(3, 1, 4, 0); 
        // white king should not be in checkmate
        assertFalse(chessBoard.isCheckmate()); 
    }

    /**
     * Test the isCheck method
     */
    @Test
    public void testIsCheck() {
        ChessBoard chessBoard = new ChessBoard();
        // Test that the white king is not in check at the start of the game
        assertFalse(chessBoard.isCheck(true));
        // Test that the black king is not in check at the start of the game
        assertFalse(chessBoard.isCheck(false));

        // Test that the white king is in check after a black queen moves to a position that threatens the king
        chessBoard.movePiece(3, 0, 4, 4);
        assertTrue(chessBoard.isCheck(true));
    }

    /**
     * Test the findKing method
     */
    @Test
    public void testFindKing() {
        ChessBoard chessBoard = new ChessBoard();
        ArrayList<ChessPiece> whitePieces = chessBoard.getWhitePieces();
        ArrayList<ChessPiece> blackPieces = chessBoard.getBlackPieces();

        // check that the white king is returned when searching the white pieces
        ChessPiece whiteKing = chessBoard.findKing(whitePieces);
        assertTrue(whiteKing instanceof King);
        assertTrue(whiteKing.isWhite());

        // check that the black king is returned when searching the black pieces
        ChessPiece blackKing = chessBoard.findKing(blackPieces);
        assertTrue(blackKing instanceof King);
        assertFalse(blackKing.isWhite());
    }

     /**
     * Test the isStalemate method
     */
    @Test
    public void testIsStalemate() {
        //create a chess board and put the game in stalemate state
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece[][] board = chessBoard.getBoard();

        //move pieces to put the game in stalemate state
        chessBoard.movePiece(1,1,2,2);
        chessBoard.movePiece(6,6,5,5);
        chessBoard.movePiece(2,2,3,3);
        chessBoard.movePiece(5,5,4,4);
        chessBoard.movePiece(3,3,4,4);
        chessBoard.movePiece(4,4,3,3);
        chessBoard.movePiece(3,3,4,4);
        chessBoard.movePiece(4,4,3,3);
        chessBoard.movePiece(3,3,4,4);
        chessBoard.movePiece(4,4,3,3);

        //assert that the game is in stalemate state
        assertTrue(chessBoard.isStalemate());
    }

    /**
     * Test the handlePawnPromotion method
     */
    @Test
    public void testHandlePawnPromotion() {
        ChessBoard chessBoard = new ChessBoard();
        Pawn pawn = new Pawn(true, 6, 0);
        chessBoard.addPiece(pawn, 6, 0);
        chessBoard.handlePawnPromotion(pawn, 6, 7);
        // check if the pawn was removed from the board
        assertNull(chessBoard.getPieceAt(6, 0));
        // check if the new piece was added to the board
        assertNotNull(chessBoard.getPieceAt(6, 7));
    }

    /**
     * Test the isLegalMove method
     */
    @Test
    public void testIsLegalMove() {
        ChessBoard chessBoard = new ChessBoard();
        ChessPiece piece = chessBoard.getPieceAt(4, 1);
        // Test a legal move
        assertTrue(chessBoard.isLegalMove(piece, 4, 3));
        // Test an illegal move
        assertFalse(chessBoard.isLegalMove(piece, 4, 4));
    }

}

