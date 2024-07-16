package main;
import pieces.Piece;

public class Move {
    //ATRIBUS
    int oldCol;
    int oldRow;
    int newCol,newRow;

    Piece piece;
    Piece capture;

    public Move(Board board ,Piece piece ,int newCol,int newRow){
        this.oldCol=piece.col;
        this.oldRow=piece.row;
        this.newCol =newCol;
        this.newRow =newRow;
        
        this.piece=piece;
        capture =board.getPiece(newCol, newRow);
    }
}
