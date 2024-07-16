package main;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import pieces.Piece;

public class Input extends MouseAdapter {
    // ATTRIBUS
    Board board;

    //CONS
    public Input(Board board){
        this.board=board;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    if(board.selectedPiece != null){
      board.selectedPiece.xPos =e.getX() - Board.TILE_SIZE / 2;            
      board.selectedPiece.yPos =e.getY() - Board.TILE_SIZE / 2;
      board.repaint();
    }
  }
                        


    @Override
    public void mousePressed(MouseEvent e) {
      int col = e.getX()/Board.TILE_SIZE;  
      int row = e.getY()/Board.TILE_SIZE; 

      Piece pieceXY = board.getPiece(col, row);
      if(pieceXY != null){
        board.selectedPiece =pieceXY;
      }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      int col = e.getX()/Board.TILE_SIZE;  
      int row = e.getY()/Board.TILE_SIZE; 

      if(board.selectedPiece != null){
        Move move = new Move(board, board.selectedPiece, col, row);
        if(board.isValidMove(move)){
          board.makeMove(move);
        }else{
          board.selectedPiece.xPos = board.selectedPiece.col * Board.TILE_SIZE;
          board.selectedPiece.yPos = board.selectedPiece.row * Board.TILE_SIZE;

        }
      }
      board.selectedPiece = null;
      board.repaint();
    }
    
}
