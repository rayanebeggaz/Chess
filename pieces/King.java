package pieces;

import java.awt.image.BufferedImage;
import main.Board;
import main.Move;

public class King extends Piece {

    public King(Board board, int col, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.col = col;
        this.row = row;
        this.xPos = col * Board.TILE_SIZE;
        this.yPos = row * Board.TILE_SIZE;
        this.isWhite = isWhite;
        this.name = "King";
        int picnumber = 0;
        this.sprite = sheet.getSubimage(picnumber * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, BufferedImage.SCALE_SMOOTH);
    }

    public Boolean isValidMovement(int col, int row) {

        return Math.abs(this.col - col) <= 1 && Math.abs(this.row - row) <= 1 || canCastel(col, row);
    }

    public Boolean canCastel(int col, int row) {

        if (this.row == row) {
            if (col == 6) {
                Piece rock = board.getPiece(7, row);
                if (rock != null && rock.isFirstMove && this.isFirstMove) {
                    return board.getPiece(5, row) == null
                            && board.getPiece(6, row) == null
                            && !board.checkScanner.isKingChecked(new Move(board, this, 5, row));
                }
            } else {
                if (col == 2) {
                    Piece rock = board.getPiece(0, row);
                    if (rock != null && rock.isFirstMove && this.isFirstMove) {
                        return board.getPiece(1, row) == null
                                && board.getPiece(2, row) == null
                                && board.getPiece(3, row) == null
                                && !board.checkScanner.isKingChecked(new Move(board, this, 3, row));
                    }
                }
            }
        }
        return false;
    }

}
