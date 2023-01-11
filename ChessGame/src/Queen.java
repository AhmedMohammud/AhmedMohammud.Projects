//Ahmed Mohammud, moha1388
public class Queen {
    public Queen(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * checks if the inputs move the queen properly
     * also checks that queen can move in any direction for however many spots as long as its not blocked by the opponent.
     * @param board the board.
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return true if the movement is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if(board.verifySourceAndDestination(this.row,this.col,endRow,endCol,isBlack)) {
            if (board.verifyVertical(this.row, this.col, endRow, endCol) || board.verifyDiagonal(this.row, this.col, endRow, endCol) || board.verifyHorizontal(this.row, this.col, endRow, endCol)) {
                return true;
            }
        }
        return false;
    }
    private int row;
    private int col;
    private boolean isBlack;
}
