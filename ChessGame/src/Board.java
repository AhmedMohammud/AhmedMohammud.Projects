//Ahmed Mohammud, moha1388
public class Board {

    // Instance variables
    private Piece[][] board;

    //TODO:
    // Construct an object of type Board using given arguments.
    public Board() {
        this.board = new Piece[8][8];

    }

    // Accessor Methods

    //TODO:
    // Return the Piece object stored at a given row and column
    public Piece getPiece(int row, int col) {

        return this.board[row][col];
    }

    //TODO:
    // Update a single cell of the board to the new piece.
    public void setPiece(int row, int col, Piece piece) {
        this.board[row][col] = piece;
        piece.setPosition(row,col);

    }

    // Game functionality methods

    //TODO:
    // Moves a Piece object from one cell in the board to another, provided that
    // this movement is legal. Returns a boolean to signify success or failure.

    /**
     * checks if the starting space is not an open space.
     * if it's not an open space then the piece can move to endRow and endCol
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns true if the move is moveable, false otherwise.
     */
    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (this.board[startRow][startCol] != null && this.board[startRow][startCol].isMoveLegal(this, endRow, endCol)){
            this.board[endRow][endCol] = this.board[startRow][startCol];
            this.board[startRow][startCol] = null;
            this.board[endRow][endCol].setPosition(endRow, endCol);
            return true;
        }
        else{
            return false;
        }
    }

    //TODO:
    // Determines whether the game has been ended, i.e., if one player's King
    // has been captured.

    /**
     * cn (counter) tracks whether there is an inputed king space, then the game is over.
     * @return return true if the game is over, true otherwise.
     */
    public boolean isGameOver() {
        int cn = 0;
        for( int x = 0; x<= 7; x++){
            for(int y = 0; y<= 7; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getCharacter() == '\u2654' || board[x][y].getCharacter() == '\u265a') {
                        cn++;
                        if (cn == 2) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    //TODO:
    // Construct a String that represents the Board object's 2D array. Return
    // the fully constructed String.
    public String toString() {
        String out = "\u2001" + "\u2001";
        int cnt = -1;
        out += "0 1 2 3 4 5 6 7";
        for(int x = 0; x <= 7; x++){
            cnt = cnt+1;
            out += "\n";
            out += cnt;
            for(int y = 0; y<= 7; y++){
                out += "|";
                if(null == board[x][y]){
                    out += "\u2001";
                }
                else {
                    out += board[x][y];
                }
            }
            out+= "|";
        }
        return out;
    }

    //TODO:
    // Sets every cell of the array to null. For debugging and grading purposes.
    public void clear() {
        for(int x = 0; x <= 7; x++){
            for(int y = 0; y <= 7; y++){
                board[x][y] = null;
            }
        }

    }

    // Movement helper functions

    //TODO:
    // Ensure that the player's chosen move is even remotely legal.
    // Returns a boolean to signify whether:
    // - 'start' and 'end' fall within the array's bounds.
    // - 'start' contains a Piece object, i.e., not null.
    // - Player's color and color of 'start' Piece match.
    // - 'end' contains either no Piece or a Piece of the opposite color.

    /**
     * Movement helper function.
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns false if the move is in bounds and that there is no piece in the spot, true otherwise.
     */
    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if(startRow < 0 || startRow > 7){
         return false;
        }
        if(startRow < 0 || startCol > 7){
            return false;
        }
        if(endRow < 0 || endRow > 7){
            return false;
        }
        if(endCol < 0 || endCol > 7){
            return false;
        }
        if(board[startRow][startCol] == null){
            return false;
        }
        if(board[startRow][startCol].getIsBlack() != isBlack){
            return false;
        }
        if(board[endRow][endCol] != null && board[endRow][endCol].getIsBlack() == isBlack){
            return false;
        }
        return true;
    }

    //TODO:
    // Check whether the 'start' position and 'end' position are adjacent to each other

    /**
     * Movement helper function. checks if adjacent movement is legal for the king.
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns true if the move is legal, false otherwise.
     */
    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        if(startRow != endRow){
            return false;
        }
        if(startCol > endCol){
            int t = startCol;
            startCol = endCol;
            endCol = t;
        }
        for(int x = startCol+1; x < endCol; x++){
            if(board[startRow][x] != null){
                return false;
            }
        }
        return true;

    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid horizontal move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one row.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.

    /**
     * Movement helper function. checks if horizontal movement is legal.
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns true if the move is legal, false otherwise.
     */
    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        if(startCol != endCol){
            return false;
        }
        if(startRow > endRow){
            int t = startRow;
            startRow = endRow;
            endRow = t;
        }
        for(int x = startRow+1; x < endRow; x++){
            if(board[startCol][x] != null){
                return false;
            }
        }
        return true;
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid vertical move.
    // Returns a boolean to signify whether:
    // - The entire move takes place on one column.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.

    /**
     * Movement helper function. checks if vertical movement is legal.
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns true if the move is legal, false otherwise.
     */
    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        if(startCol != endCol){
            return false;
        }
        if (startRow > endRow) {
            int t = startRow;
            startRow = endRow;
            endRow = t;
        }
        for (int x = startRow + 1; x < endRow; x++) {
            if (board[x][startCol] != null) {
                return false;
            }
        }
        return true;
    }

    //TODO:
    // Checks whether a given 'start' and 'end' position are a valid diagonal move.
    // Returns a boolean to signify whether:
    // - The path from 'start' to 'end' is diagonal... change in row and col.
    // - All spaces directly between 'start' and 'end' are empty, i.e., null.

    /**
     * Movement helper function. checks if diagnal movement is legal
     * @param startRow the row of the starting spot
     * @param startCol the col of the starting spot
     * @param endRow the row of the destination
     * @param endCol the col of the destination
     * @return returns true if the move is legal, false otherwise.
     */
    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        int x = Math.abs(startRow - endRow) / (startRow - endRow);
        int y = Math.abs(startCol - endCol) / (startCol - endCol);
        int a = startRow + x;
        int b = startCol + y;
        while((a <= Math.max(startRow,endRow) && a >= Math.min(startRow,endRow)) && b <= Math.max(startCol, endCol) && a >= Math.min(startCol, endCol) ){
            if(board[a][b] != null){
                return false;
            }
            a += x;
            b += y;
        }
        return true;
    }
}
