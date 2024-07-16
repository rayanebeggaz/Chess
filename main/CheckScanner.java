package main;

import pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board) {
        this.board = board;
    }

    public Boolean isKingChecked(Move move) {
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;
        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return hitByRock(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1)
                || hitByRock(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0)
                || hitByRock(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1)
                || hitByRock(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0)
                || hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1)
                || hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1)
                || hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1)
                || hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1)
                || hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow)
                || hiyByPawn(move.newCol, move.newRow, king, kingCol, kingRow)
                || hitByKing(king, kingCol, kingRow);
    }

    private Boolean hitByRock(int col, int row, Piece King, int KingCol, int KingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++) {
            if (KingCol + (i * colVal) == col && KingRow + (i * rowVal) == row) {
                break;
            }
            Piece piece = board.getPiece(KingCol + (i * colVal), KingRow + (i * rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, King) && (piece.name.equals("Rock") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;

    }

    private Boolean hitByBishop(int col, int row, Piece King, int KingCol, int KingRow, int colVal, int rowVal) {
        for (int i = 1; i < 8; i++) {
            if (KingCol - (i * colVal) == col && KingRow - (i * rowVal) == row) {
                break;
            }
            Piece piece = board.getPiece(KingCol - (i * colVal), KingRow - (i * rowVal));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, King) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))) {
                    return true;
                }
                break;
            }
        }
        return false;

    }

    private Boolean hitByKnight(int col, int row, Piece king, int kingCol, int kingRow) {
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row)
                || checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row)
                || checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row)
                || checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row)
                || checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row)
                || checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row)
                || checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row)
                || checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private Boolean checkKnight(Piece p, Piece k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private Boolean hitByKing(Piece king, int kingCol, int kingRow) {
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king)
                || checkKing(board.getPiece(kingCol + 1, kingRow - 1), king)
                || checkKing(board.getPiece(kingCol, kingRow - 1), king)
                || checkKing(board.getPiece(kingCol - 1, kingRow), king)
                || checkKing(board.getPiece(kingCol + 1, kingRow), king)
                || checkKing(board.getPiece(kingCol - 1, kingRow + 1), king)
                || checkKing(board.getPiece(kingCol + 1, kingRow + 1), king)
                || checkKing(board.getPiece(kingCol, kingRow + 1), king);
    }

    private Boolean checkKing(Piece p, Piece k) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private Boolean hiyByPawn(int col, int row, Piece king, int kingCol, int kingRow) {
        int colorVal = king.isWhite ? -1 : 1;
        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row)
                || checkPawn(board.getPiece(kingCol - 1, kingRow + colorVal), king, col, row);
    }

    private Boolean checkPawn(Piece p, Piece k, int col, int row) {
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }

    public Boolean isGameOver(Piece king) {
        for (Piece p : board.pieceList) {
            if (board.sameTeam(p, king)) {
                if (p == king) {
                    board.selectedPiece = p;
                } else {
                    board.selectedPiece = null;
                }
                for (int row = 0; row < board.ROWS; row++) {
                    for (int col = 0; col < board.COLS; col++) {
                        Move move = new Move(board, p, col, row);
                        if (board.isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
