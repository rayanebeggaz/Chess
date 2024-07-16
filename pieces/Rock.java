package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Rock extends Piece {

    public Rock(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILE_SIZE;
        this.yPos = row * Board.TILE_SIZE;
        this.isWhite = isWhite;
        this.name = "Rock";
        int picnumber = 4;
        this.sprite = sheet.getSubimage(picnumber * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public Boolean isValidMovement(int col, int row) {
        return this.col == col || this.row == row;
    }

    public Boolean isMoveCollideWithPiece(int col, int row) {
        //left
        if (this.col > col) {
            for (int c = this.col - 1; c > col; c--) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }
        // right
        if (this.col < col) {
            for (int c = this.col + 1; c < col; c++) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }
        // up
        if (this.row > row) {
            for (int r = this.row - 1; r > row; r--) {
                if (board.getPiece(this.col, r) != null) {
                    return true;
                }
            }
        }
        // down
        if (this.row < row) {
            for (int r = this.row + 1; r < row; r++) {
                if (board.getPiece(this.col, r) != null) {
                    return true;
                }
            }
        }
        return false;
    }
}
