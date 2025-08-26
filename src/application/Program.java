package application;

import chess.*;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main( String[] args ) {

        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (chessMatch.getPromoted() != null) {
                    System.out.print("Qual peça será a promovida: B/N/Q/R: ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.print("Peça não existe! Qual peça será a promovida: B/N/Q/R: ");
                        type = sc.nextLine().toUpperCase();

                    }
                    chessMatch.replacePromotedPiece(type);
                }

                if (chessMatch.isDrawByStalemate()) {
                    UI.clearScreen();
                    UI.printMatch(chessMatch, captured);
                    System.out.println("Empate por afogamento!");
                    break;
                }

                if (chessMatch.isFiftyMoveDraw()) {
                    UI.clearScreen();
                    UI.printMatch(chessMatch, captured);
                    System.out.println("Empate declarado por 50 movimentos sem captura ou movimento de peão!");
                    break;
                }

                if (chessMatch.isDrawByThreefoldRepetition()) {
                    UI.clearScreen();
                    UI.printMatch(chessMatch, captured);
                    System.out.println("Empate por 3 repetições de posição!");
                    break;
                }
                if (chessMatch.isDrawByInsufficientMaterial()) {
                    UI.clearScreen();
                    UI.printMatch(chessMatch, captured);
                    System.out.println("Empate por material insuficiente!");
                    break;
                }
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }
}
