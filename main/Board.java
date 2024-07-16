package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import pieces.*;

public class Board extends JPanel {

    //ATTRIBUTS
    public static int TILE_SIZE = 85;

    int COLS = 8;
    int ROWS = 8;
    public Piece selectedPiece;
    public Input input = new Input(this);
    ArrayList<Piece> pieceList = new ArrayList<>();
    public int enPassentTile = -1;
    public CheckScanner checkScanner = new CheckScanner(this);
    private Boolean isWhiteToMove = true;
    private Boolean isGameOver = false;
    public String message = "";

    // @CONST
    public Board() {
        this.setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
    }

    //METHODS
    public void addPieces() {
        // // add knights
        pieceList.add(new Knight(this, 1, 0, false));
        pieceList.add(new Knight(this, 6, 0, false));
        pieceList.add(new Knight(this, 1, 7, true));
        pieceList.add(new Knight(this, 6, 7, true));
        // add kings
        pieceList.add(new King(this, 4, 7, true));
        pieceList.add(new King(this, 4, 0, false));
        // add queens
        pieceList.add(new Queen(this, 3, 7, true));
        pieceList.add(new Queen(this, 3, 0, false));
        // // add bishops
        pieceList.add(new Bishop(this, 2, 7, true));
        pieceList.add(new Bishop(this, 5, 7, true));
        pieceList.add(new Bishop(this, 2, 0, false));
        pieceList.add(new Bishop(this, 5, 0, false));
        // // add rocks
        pieceList.add(new Rock(this, 0, 7, true));
        pieceList.add(new Rock(this, 7, 7, true));
        pieceList.add(new Rock(this, 0, 0, false));
        pieceList.add(new Rock(this, 7, 0, false));
        // // add pawns
        for (int i = 0; i < 8; i++) {
            pieceList.add(new Pawn(this, i, 6, true));
            pieceList.add(new Pawn(this, i, 1, false));
        }
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : pieceList) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }

    public Boolean isValidMove(Move move) {

        return !isGameOver && (move.piece.isWhite == isWhiteToMove) && !sameTeam(move.piece, move.capture)
                && move.piece.isValidMovement(move.newCol, (move.newRow))
                && !move.piece.isMoveCollideWithPiece(move.newCol, move.newRow)
                && !checkScanner.isKingChecked(move);
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    public int getTilenum(int col, int row) {
        return row * ROWS + col;
    }

    Piece findKing(Boolean isWhite) {
        for (Piece piece : pieceList) {
            if (isWhite == piece.isWhite && piece.name.equals("King")) {
                return piece;
            }
        }
        return null;
    }

    public void makeMove(Move move) {
        if (move.piece.name.equals("Pawn")) {
            movePawn(move);
        } else if (move.piece.name.equals("King")) {
            moveKing(move);
        }
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
        move.piece.yPos = move.newRow * TILE_SIZE;
        move.piece.xPos = move.newCol * TILE_SIZE;
        move.piece.isFirstMove = false;
        capture(move.capture);
        isWhiteToMove = !isWhiteToMove;
        updateGame();
    }

    private void moveKing(Move move) {
        if (Math.abs(move.piece.col - move.newCol) == 2) {
            Piece rock;
            if (move.piece.col < move.newCol) {
                rock = getPiece(7, move.piece.row);
                rock.col = 5;
            } else {
                rock = getPiece(0, move.piece.row);
                rock.col = 3;
            }
            rock.xPos = rock.col * TILE_SIZE;
        }
    }

    private void movePawn(Move move) {
        // en passant 
        int IndxColor = move.piece.isWhite ? 1 : -1;

        if (getTilenum(move.newCol, move.newRow) == enPassentTile) {
            move.capture = getPiece(move.newCol, move.newRow + IndxColor);
        }
        if (Math.abs(move.piece.row - move.newRow) == 2) {
            enPassentTile = getTilenum(move.newCol, move.newRow + IndxColor);
        } else {
            enPassentTile = -1;
        }

        // promotions
        IndxColor = move.piece.isWhite ? 0 : 7;
        if (move.newRow == IndxColor) {
            promotePawn(move);
        }

    }

    private void promotePawn(Move move) {
        pieceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
        capture(move.piece);
    }

    public void capture(Piece piece) {
        pieceList.remove(piece);
    }

    private void updateGame() {
        Piece king = findKing(isWhiteToMove);
        if (checkScanner.isGameOver(king)) {
            if (checkScanner.isKingChecked(new Move(this, king, king.col, king.row))) {
                message = isWhiteToMove ? "Black Wins" : "White Wins";
            } else {
                message = "statment";

            }
            isGameOver = true;
        } else if (insuffPieces(true) && insuffPieces(true)) {
            message = "Insuffisant Pieces !";
            isGameOver = true;
        }

    }

    private Boolean insuffPieces(Boolean isWhite) {
        ArrayList<String> names = pieceList.stream()
                .filter(p -> p.isWhite == isWhite)
                .map(p -> p.name)
                .collect(Collectors.toCollection(ArrayList::new));
        if (names.contains("Queen") || names.contains("Rock") || names.contains("Pawn")) {
            return false;
        }
        return names.size() < 3;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                g2d.setColor((c + r) % 2 == 0 ? new Color(0xb3cee5) : new Color(0x005A9C));
                g2d.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        if (selectedPiece != null) {
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {
                        g2d.setColor(new Color(0, 255, 0, 200));
                        g2d.fillRect(c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }

            }
        }

        if (!message.equals("")) {
            // Dessiner le message au centre du panneau
            int fontSize = 20;
            g.setFont(new Font("Arial", Font.BOLD, fontSize));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(message);
            int textHeight = fm.getHeight();
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() / 2) + fm.getAscent() / 2;

            g.setColor(Color.BLACK);
            g.fillRect(x - 10, y - textHeight, textWidth + 20, textHeight + 5);

            g.setColor(Color.GREEN);
            g.drawString(message, x, y);
        }
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }

    }
}
