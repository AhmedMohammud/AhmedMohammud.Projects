//Ahmed Mohammud, moha1388
public class Knight {

    public Knight(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    /**
     * checks for both vertical and horizontal movements b/c the Knight moves 2 spots horizontal or vertical and 1 last spot to make the 'L' shape.
     * checks the inputs for the Knight if it works properly.
     * @param board the board
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return true if the movement is legal, false otherwise.
     */
    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if(board.verifySourceAndDestination(this.row,this.col,endRow,endCol,isBlack)){
            int rowD = Math.abs(endRow - this.row);
            int colD = Math.abs(endCol - this.col);

            if((rowD == 1 && colD == 2) || (colD == 1 && rowD == 2)){
                return true;
            }
        }
        return false;
    }
    private int row;
    private int col;
    private boolean isBlack;
}
