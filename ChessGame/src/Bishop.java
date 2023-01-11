//Ahmed Mohammud, moha1388
public class Bishop {
    /**
     * creates bishop
     * @param row
     * @param col
     * @param isBlack
     */
    public Bishop(int row, int col, boolean isBlack) {
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * checks for diagnal movements b/c that's how bishops work in chess.
     * checks the inputs for the bishop if it works properly.
     * @param board the board.
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return true if the moves are legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol) {
        if (board.verifySourceAndDestination(this.row,this.col,endRow,endCol,isBlack)){
            if (board.verifyDiagonal(this.row,this.col,endRow,endCol)){
                return true;
            }
        }
        return false;
    }
    private int row;
    private int col;
    private boolean isBlack;
}
