package pieces;

import java.awt.image.BufferedImage;
import main.Board;

public class Knight extends Piece {

    public Knight(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILE_SIZE;
        this.yPos = row * Board.TILE_SIZE;
        this.isWhite = isWhite;
        this.name = "knight";
        int picnumber = 3;
        this.sprite = sheet.getSubimage(picnumber * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public Boolean isValidMovement(int col, int row) {
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
    }
}
