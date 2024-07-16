package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Pawn extends Piece {

    public Pawn(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILE_SIZE;
        this.yPos = row * Board.TILE_SIZE;
        this.isWhite = isWhite;
        this.name = "Pawn";
        int picnumber = 5;
        this.sprite = sheet.getSubimage(picnumber * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    @Override
    public Boolean isValidMovement(int col, int row) {
        int IndxColor = isWhite ? -1 : 1;
        if ((this.col == col) && (this.row == row - IndxColor) && (board.getPiece(col, row) == null)) {
            return true;
        }
        if (super.isFirstMove && (this.col == col) && (this.row == row - IndxColor * 2) && (board.getPiece(col, row) == null) && (board.getPiece(col, row - IndxColor) == null)) {
            return true;
        }
        if ((this.col == col - 1) && (this.row == row - IndxColor) && (board.getPiece(col, row) != null)) {
            return true;
        }
        if ((this.col == col + 1) && (this.row == row - IndxColor) && board.getPiece(col, row) != null) {
            return true;
        }
        if (board.getTilenum(col, row) == board.enPassentTile && col == this.col - 1 && row == this.row - IndxColor && board.getPiece(col, row - IndxColor) != null) {
            return true;
        }
        if (board.getTilenum(col, row) == board.enPassentTile && col == this.col + 1 && row == this.row - IndxColor && board.getPiece(col, row - IndxColor) != null) {
            return true;
        }

        return false;
    }
}
