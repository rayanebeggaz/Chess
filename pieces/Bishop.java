package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Bishop extends Piece {

    public Bishop(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILE_SIZE;
        this.yPos = row * Board.TILE_SIZE;
        this.isWhite = isWhite;
        this.name = "Bishop";
        int picnumber = 2;
        this.sprite = sheet.getSubimage(picnumber * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public Boolean isValidMovement(int col, int row) {
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    public Boolean isMoveCollideWithPiece(int col, int row) {
// up left 
        if (this.col > col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col - i, this.row - i) != null) {
                    return true;
                }
            }
        }
// up right 
        if (this.col < col && this.row > row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col + i, this.row - i) != null) {
                    return true;
                }
            }
        }
// down left 
        if (this.col > col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col - i, this.row + i) != null) {
                    return true;
                }
            }
        }
// down right 
        if (this.col < col && this.row < row) {
            for (int i = 1; i < Math.abs(this.col - col); i++) {
                if (board.getPiece(this.col + i, this.row + i) != null) {
                    return true;
                }
            }
        }
        return false;

    }
}
