//Ahmed Mohammud, moha1388
public class King {
    public King(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * check if the inputs move the King properly.
     * also, checks that movement of the can be in any direction but, only one spot.
     * @param board the board
     * @param endRow row of the destination
     * @param endCol col of the destination
     * @return true if the movement is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if(board.verifySourceAndDestination(this.row,this.col,endRow,endCol,isBlack)){
            if(board.verifyAdjacent(this.row,this.col,endRow,endCol)){
                return true;
            }
        }
        return false;
    }
    private int row;
    private int col;
    private boolean isBlack;
}
