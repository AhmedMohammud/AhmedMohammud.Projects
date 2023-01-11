//Ahmed Mohammud, moha1388
public class Rook {
    public Rook(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }
    /**
     * checks for vertical and horizontal movements b/c that's how rook work in chess.
     * checks the inputs for the rook if it works properly.
     * @param board the board
     * @param endRow row of the destination
     * @param endCol col of the destination
     * @return true if the movement is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row, this.col, endRow, endCol, isBlack)) {
            if (board.verifyHorizontal(this.row, this.col, endRow, endCol) || board.verifyVertical(this.row, this.col, endRow, endCol)) {
                return true;
            }
        }
        return false;
    }
    private int row;
    private int col;
    private boolean isBlack;
}
