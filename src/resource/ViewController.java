package resource;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ViewController {

    @FXML
    private GridPane boardGrid;
    private Button[][] boardButtons = new Button[8][8];
    private Label[][] boardLabels = new Label[8][8];

    private ChessMatch chessMatch;
    private Position selectedPosition = null;

    @FXML
    public void initialize() {
        chessMatch = new ChessMatch();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Label label = new Label();
                boardLabels[row][col] = label;
                boardGrid.add(label, col, row);
                label.setStyle("-fx-font-size: 24px; -fx-alignment: center;");
            }
        }
        for (Node node : boardGrid.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            row = row == null ? 0 : row;
            col = col == null ? 0 : col;
            if (node instanceof Button) {
                boardButtons[row][col] = (Button) node;
                final int r = row, c = col;
                boardButtons[row][col].setOnAction(e -> handleCellClick(r, c));
            }
        }
        updateBoard();
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = chessMatch.getPieces()[row][col];
                if (piece != null) {
                    String letter = piece.toString();
                    String emoji = translateLetterToEmoji(letter, piece.getColor());
                    boardLabels[row][col].setText(emoji);
                } else {
                    boardLabels[row][col].setText("");
                }
            }
        }
    }

    private void handleCellClick(int row, int col) {
        if (selectedPosition == null) {
            ChessPiece piece = chessMatch.getPieces()[row][col];
            if (piece != null) {
                selectedPosition = new Position(row, col);
            }
        } else {
            char sourceColumn = (char) ('a' + selectedPosition.getColumn());
            int sourceRow = 8 - selectedPosition.getRow();
            char targetColumn = (char) ('a' + col);
            int targetRow = 8 - row;

            ChessPosition source = new ChessPosition(sourceColumn, sourceRow);
            ChessPosition target = new ChessPosition(targetColumn, targetRow);
            chessMatch.performChessMove(source, target);
            selectedPosition = null;
            updateBoard();
        }
    }

    private String translateLetterToEmoji(String letter, Color color) {
        switch (letter.toUpperCase()) {
            case "K":
                return color == Color.WHITE ? "♔" : "♚";
            case "Q":
                return color == Color.WHITE ? "♕" : "♛";
            case "R":
                return color == Color.WHITE ? "♖" : "♜";
            case "B":
                return color == Color.WHITE ? "♗" : "♝";
            case "N":
                return color == Color.WHITE ? "♘" : "♞";
            case "P":
                return color == Color.WHITE ? "♙" : "♟";
            default:
                return "";
        }
    }
}