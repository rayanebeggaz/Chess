package pieces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Board;
import main.Move;

public abstract class Piece {

    //ATTRIBUS
    public String name;
    public int value;
    public boolean isWhite;
    Board board;
    public int col, row;
    public int xPos, yPos;
    public Boolean isFirstMove = true;

    BufferedImage sheet;

    {
        try {
            sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/pieces.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    protected int sheetScale = sheet.getWidth() / 6;
    Image sprite;

    //CONS
    public Piece(Board board) {
        this.board = board;
    }

    // REQUETES 
    public Boolean isValidMovement(int col, int row) {
        return true;
    }

    public Boolean isMoveCollideWithPiece(int col, int row) {
        return false;
    }

    //METHODS
    public void paint(Graphics2D g2d) {
        if (name.equals("King") && board.checkScanner.isKingChecked(new Move(board, this, col, row))) {
            g2d.setColor(new Color(255, 0, 0));
            g2d.fillRect(col * Board.TILE_SIZE, row * Board.TILE_SIZE, Board.TILE_SIZE, Board.TILE_SIZE);
        }
        g2d.drawImage(sprite, xPos, yPos, null);

    }
}
