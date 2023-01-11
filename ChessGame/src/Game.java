//Ahmed Mohammud, moha1388
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        //new board (boardGame)
        Board boardGame = new Board();
        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",boardGame);
        System.out.println("Welcome to CHESS!");
        System.out.println(boardGame);
        int turn = 1;
        //the while loop starts the game.
        while(boardGame.isGameOver() == false){
            boolean isBlack = (turn % 2 == 0);
            Scanner input = new Scanner(System.in);
            String move;
            int startRow;
            int startCol;
            int endRow;
            int endCol;
            if(isBlack){
                System.out.println("White's Turn");
            }
            else{
                System.out.println("Black's Turn");
            }
            System.out.println("Make your move: [Start Row] [Start Col] [End Row] [End Col]");
            move = input.nextLine();
            String list[] = move.split(" ");
            startRow = Integer.parseInt(list[0]);
            startCol = Integer.parseInt(list[1]);
            endRow = Integer.parseInt(list[2]);
            endCol = Integer.parseInt(list[3]);
            //checks for legal movements
            if(boardGame.getPiece(startRow, startCol).isMoveLegal(boardGame,endRow,endCol) && boardGame.verifySourceAndDestination(startRow, startCol, endRow, endCol, isBlack)){
                boardGame.movePiece(startRow,startCol,endRow,endCol);
                boardGame.getPiece(endRow,endCol).promote(endRow,endCol,isBlack);
                turn++;
                System.out.println(boardGame);
            }
            else{
                System.out.println("Illegal Move! Try Again.");
            }
        }
        //finds the winner of the game.
        if((turn%2) == 1){
            System.out.println("Black Wins");
        }
        else{
            System.out.println("White Wins");
        }
    }
}