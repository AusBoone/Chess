import org.junit.Test;
import static org.junit.Assert.*;
import ChessBoard;
import ComputerPlayer;

public class ComputerPlayerTest {

    @Test
    public void testGetMove() {
        ComputerPlayer player = new ComputerPlayer();
        ChessBoard board = new ChessBoard();

        // test that the method returns a valid move (x1, y1, x2, y2)
        int[] move = player.getMove(board);
        assertTrue(move.length == 4);
        assertTrue(move[0] >= 0 && move[0] < 8);
        assertTrue(move[1] >= 0 && move[1] < 8);
        assertTrue(move[2] >= 0 && move[2] < 8);
        assertTrue(move[3] >= 0 && move[3] < 8);

        // test that the returned move is a legal move
        ChessPiece piece = board.getPieceAt(move[0], move[1]);
        assertTrue(board.isLegalMove(piece, move[2], move[3]));
    }
}
