package chess;

import DAO.DAOPlayer;
import Players.Player;
import static chess.ChessGame.pl1;
import static chess.ChessGame.pl2;
import static java.lang.Math.abs;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Piece {

    static boolean turn = true;
    boolean firstMove = true;
    int xp;
    int yp;
    int x;
    int y;
    boolean isWhite;
    LinkedList<Piece> ps;
    String name;

    public Piece(int xp, int yp, boolean isWhite, String n, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        x = xp * 64;
        y = yp * 64;
        this.isWhite = isWhite;
        this.ps = ps;
        name = n;
        ps.add(this);
    }

    public void move(int xp, int yp) {
        System.out.println((pl1.kx) + " " + (pl1.ky));
        if (Piece.turn == this.isWhite && this.mouvement(xp, yp) && this.pices_in_the_way(xp, yp) && this.ownPiece(xp, yp)) {
            int xCh = this.xp;
            int yCh = this.yp;
            this.xp = xp;
            this.yp = yp;
            x = xp * 64;
            y = yp * 64;

            if (this.name.equals("king")) {
                if (this.isWhite == pl1.getColor().equals("White")) {
                    ChessGame.pl1.kx = xp;
                    ChessGame.pl1.ky = yp;
                } else {
                    ChessGame.pl2.kx = xp;
                    ChessGame.pl2.ky = yp;
                }
            }
            if (check(ChessGame.pl1.kx, ChessGame.pl1.ky) || check(ChessGame.pl2.kx, ChessGame.pl2.ky)) {
                this.xp = xCh;
                this.yp = yCh;
                x = xCh * 64;
                y = yCh * 64;
            }
            if (this.xp != xCh || this.yp != yCh) {
                Piece.turn = !Piece.turn;
                this.firstMove = false;
            }
        } else {
            x = this.xp * 64;
            y = this.yp * 64;
        }
    }

    public void kill() {
        ps.remove(this);
    }

    public boolean ownPiece(int xp, int yp) {
        boolean res = true;
        if (ChessGame.getPiece(xp * 64, yp * 64) != null) {
            if (ChessGame.getPiece(xp * 64, yp * 64).isWhite != this.isWhite) {
                ChessGame.getPiece(xp * 64, yp * 64).kill();
                res = true;
            } else {
                res = false;
            }

        }
        return res;
    }

    public boolean ownPieceK(int xp, int yp) {
        boolean res = true;
        if (ChessGame.getPiece(xp * 64, yp * 64) != null) {
            if (ChessGame.getPiece(xp * 64, yp * 64).isWhite != this.isWhite) {
                res = true;
            } else {
                res = false;
            }

        }
        return res;
    }

    public boolean mouvement(int xp, int yp) {
        int x = abs(this.xp - xp);
        int y = abs(this.yp - yp);
        if (this.name.equals("rook")) {
            return x == 0 || y == 0;

        }
        if (this.name.equals("knight")) {
            return (x == 2 || y == 2) && x + y == 3;
        }
        if (this.name.equals("bishop")) {
            return x == y;
        }
        if (this.name.equals("queen")) {

            return x == y || (x == 0 || y == 0);
        }
        if (this.name.equals("king")) {
            return x + y == 1 || rookage(xp, yp);
        }
        if (this.name.equals("pawn")) {
            if (this.isWhite) {
                return ((this.yp - yp == 1 || (this.yp - yp == 2 && this.yp == 6)) && this.xp - xp == 0) || (this.yp - yp == 1 && x == 1);
            } else {

                return ((yp - this.yp == 1 || (yp - this.yp == 2 && this.yp == 1)) && this.xp - xp == 0) || (yp - this.yp == 1 && x == 1);
            }
        }
        return false;

    }

    public boolean pices_in_the_way(int xp, int yp) {
        int x = abs(this.xp - xp);
        int y = abs(this.yp - yp);
        int i;
        if (this.name.equals("rook")) {
            if (this.xp > xp) {
                for (i = xp + 1; i < this.xp; i++) {
                    if (ChessGame.getPiece(i * 64, yp * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp) {
                for (i = this.xp + 1; i < xp; i++) {
                    if (ChessGame.getPiece(i * 64, yp * 64) != null) {
                        return false;
                    }
                }
            } else if (this.yp > yp) {
                for (i = yp + 1; i < this.yp; i++) {
                    if (ChessGame.getPiece(xp * 64, i * 64) != null) {
                        return false;
                    }
                }
            } else if (this.yp < yp) {
                for (i = this.yp + 1; i < yp; i++) {
                    if (ChessGame.getPiece(xp * 64, i * 64) != null) {
                        return false;
                    }
                }
            }
            return true;
        }

        if (this.name.equals("bishop")) {
            if (this.xp > xp && this.yp < yp) {
                for (i = 1; i < this.xp - xp; i++) {
                    if (ChessGame.getPiece((this.xp - i) * 64, (this.yp + i) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp && this.yp > yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    if (ChessGame.getPiece((i + this.xp) * 64, (this.yp - i) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp && this.yp < yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    if (ChessGame.getPiece((i + this.xp) * 64, (i + this.yp) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp > xp && this.yp > yp) {
                for (i = 1; i < this.xp - xp; i++) {

                    if (ChessGame.getPiece((i + xp) * 64, (yp + i) * 64) != null) {
                        return false;
                    }
                }
            }

            return true;
        }

        if (this.name.equals("queen")) {

            if (this.xp > xp && this.yp < yp) {
                for (i = 1; i < this.xp - xp; i++) {
                    if (ChessGame.getPiece((this.xp - i) * 64, (this.yp + i) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp && this.yp > yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    if (ChessGame.getPiece((i + this.xp) * 64, (this.yp - i) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp && this.yp < yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    if (ChessGame.getPiece((i + this.xp) * 64, (i + this.yp) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp > xp && this.yp > yp) {
                for (i = 1; i < this.xp - xp; i++) {

                    if (ChessGame.getPiece((i + xp) * 64, (yp + i) * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp > xp) {
                for (i = xp + 1; i < this.xp; i++) {
                    if (ChessGame.getPiece(i * 64, yp * 64) != null) {
                        return false;
                    }
                }
            } else if (this.xp < xp) {
                for (i = this.xp + 1; i < xp; i++) {
                    if (ChessGame.getPiece(i * 64, yp * 64) != null) {
                        return false;
                    }
                }
            } else if (this.yp > yp) {
                for (i = yp + 1; i < this.yp; i++) {
                    if (ChessGame.getPiece(xp * 64, i * 64) != null) {
                        return false;
                    }
                }
            } else if (this.yp < yp) {
                for (i = this.yp + 1; i < yp; i++) {
                    if (ChessGame.getPiece(xp * 64, i * 64) != null) {
                        return false;
                    }
                }
            }
            return true;
        }

        if (this.name.equals("pawn")) {
            if (ChessGame.getPiece((xp) * 64, (yp) * 64) != null && x == 0 && (y == 1 || y == 2)) {
                return false;
            }
            if (ChessGame.getPiece((xp) * 64, (yp) * 64) == null && x == 1 && y == 1) {
                return false;
            }
        }
        return true;
    }

    public boolean rookage(int xp, int yp) {
        int x = abs(this.xp - xp);
        int y = abs(this.yp - yp);
        if (this.firstMove && x == 2 && y == 0) {
            if (this.xp > xp && this.isWhite) {
                Piece p = ChessGame.getPiece((xp - 2) * 64, (yp) * 64);
                if (p.firstMove && p.pices_in_the_way(this.xp, this.yp)) {
                    p.xp = xp + 1;
                    p.x = (xp + 1) * 64;
                    p.firstMove = false;
                    return true;
                }
            } else if (this.xp < xp && this.isWhite) {
                Piece p = ChessGame.getPiece((xp + 1) * 64, (yp) * 64);
                if (p.firstMove && p.pices_in_the_way(this.xp, this.yp)) {
                    p.xp = xp - 1;
                    p.x = (xp - 1) * 64;
                    p.firstMove = false;
                    return true;
                }
            } else if (this.xp > xp && !this.isWhite) {
                Piece p = ChessGame.getPiece((xp - 2) * 64, (yp) * 64);
                if (p.firstMove && p.pices_in_the_way(this.xp, this.yp)) {
                    p.xp = xp + 1;
                    p.x = (xp + 1) * 64;
                    p.firstMove = false;
                    return true;
                }
            } else if (this.xp < xp && !this.isWhite) {
                Piece p = ChessGame.getPiece((xp + 1) * 64, (yp) * 64);
                if (p.firstMove && p.pices_in_the_way(this.xp, this.yp)) {
                    p.xp = xp - 1;
                    p.x = (xp - 1) * 64;
                    p.firstMove = false;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean check(int xp, int yp) {
        boolean res = false;
        for (Piece p : ps) {

            if (Piece.turn == p.isWhite && p.mouvement(xp, yp) && p.pices_in_the_way(xp, yp) && p.ownPieceK(xp, yp)) {
                System.out.println("kechh hhhhhhhh");
                res = p.checkMate(xp, yp) && p.cover_king(xp, yp) && !kingcanmove(xp, yp);
                if (res) {
                    boolean white = !ChessGame.getPiece(xp * 64, yp * 64).isWhite;
                    if (pl1.getColor().equals("White") == white) {
                        Piece.winnier(pl1);
                    } else {
                        Piece.winnier(pl2);
                    }

                    res = !res;
                }
            } else if (Piece.turn != p.isWhite && p.mouvement(xp, yp) && p.pices_in_the_way(xp, yp) && p.ownPieceK(xp, yp)) {
                System.out.println("kechh");
                res = true;
            }
        }
        return res;

    }

    public boolean checkMate(int xp, int yp) {
        boolean res = true;
        for (Piece p : ps) {

            if (p.mouvement(this.xp, this.yp) && p.pices_in_the_way(this.xp, this.yp) && p.ownPieceK(this.xp, this.yp)) {
                System.out.println("kechh ama 3andk 7all");
                res = false;
            }
        }
        return res;
    }

    public boolean cover_king(int xp, int yp) {
        int x = abs(this.xp - xp);
        int y = abs(this.yp - yp);
        int i;
        boolean res = true;
        if (this.name.equals("rook")) {
            if (this.xp > xp) {
                for (i = xp + 1; i < this.xp; i++) {
                    res = yamna3_wallee(i, yp);
                    System.out.println("1");
                }
            } else if (this.xp < xp) {
                for (i = this.xp + 1; i < xp; i++) {
                    res = yamna3_wallee(i, yp);
                    System.out.println("2");
                }
            } else if (this.yp > yp) {
                for (i = yp + 1; i < this.yp; i++) {
                    res = yamna3_wallee(xp, i);
                    System.out.println("3");
                }
            } else if (this.yp < yp) {
                for (i = this.yp + 1; i < yp; i++) {
                    res = yamna3_wallee(xp, i);
                    System.out.println("4");
                }
            }

        }

        if (this.name.equals("bishop")) {
            if (this.xp > xp && this.yp < yp) {
                for (i = 1; i < this.xp - xp; i++) {
                    System.out.println("5");
                    res = yamna3_wallee(this.xp - i, this.yp + i);
                }
            } else if (this.xp < xp && this.yp > yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    System.out.println("6");
                    res = yamna3_wallee(i + this.xp, this.yp - i);
                }
            } else if (this.xp < xp && this.yp < yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    System.out.println("8");
                    res = yamna3_wallee(i + this.xp, i + this.yp);
                }
            } else if (this.xp > xp && this.yp > yp) {
                for (i = 1; i < this.xp - xp; i++) {

                    System.out.println("9");
                    res = yamna3_wallee(i + xp, i + yp + i);
                }
            }

        }

        if (this.name.equals("queen")) {

            if (this.xp > xp && this.yp < yp) {
                for (i = 1; i < this.xp - xp; i++) {
                    System.out.println("10");
                    res = yamna3_wallee(this.xp - i, this.yp + i);
                }
            } else if (this.xp < xp && this.yp > yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    System.out.println("11");
                    res = yamna3_wallee(i + this.xp, this.yp - i);
                }
            } else if (this.xp < xp && this.yp < yp) {
                for (i = 1; i < xp - this.xp; i++) {
                    System.out.println("12");
                    res = yamna3_wallee(i + this.xp, i + this.yp);
                }
            } else if (this.xp > xp && this.yp > yp) {
                for (i = 1; i < this.xp - xp; i++) {
                    System.out.println("13");
                    res = yamna3_wallee(i + xp, yp + i);
                }
            } else if (this.xp > xp) {
                for (i = xp + 1; i < this.xp; i++) {
                    System.out.println("14");
                    res = yamna3_wallee(i, yp);
                }
            } else if (this.xp < xp) {
                for (i = this.xp + 1; i < xp; i++) {
                    System.out.println("15");
                    res = yamna3_wallee(i, yp);
                }
            } else if (this.yp > yp) {
                for (i = yp + 1; i < this.yp; i++) {
                    System.out.println("16");
                    res = yamna3_wallee(xp, i);
                }
            } else if (this.yp < yp) {
                for (i = this.yp + 1; i < yp; i++) {
                    System.out.println("17");
                    res = yamna3_wallee(xp, i);
                }
            }

        }

        if (this.name.equals("pawn")) {
            if (!yamna3_wallee(xp, yp) && x == 0 && (y == 1 || y == 2)) {
                System.out.println("18");
                res = false;
            }

        }

        return res;
    }

    public boolean yamna3_wallee(int x, int y) {
        boolean res = true;
        for (Piece p : ps) {

            if (Piece.turn != p.isWhite && p.mouvement(x, y) && p.pices_in_the_way(x, y)) {
                System.out.println(p.name);
                res = false;
            }
        }
        return res;
    }

    public boolean kingcanmove(int xp, int yp) {
        Piece p = ChessGame.getPiece(xp * 64, yp * 64);
        if (xp == 0 && yp == 0) {
            return (p.mouvement(xp + 1, yp) && p.pices_in_the_way(xp + 1, yp) && p.ownPieceK(xp + 1, yp)) || (p.mouvement(xp, yp + 1) && p.pices_in_the_way(xp, yp + 1) && p.ownPieceK(xp, yp + 1));
        } else if (xp == 0 && yp == 7) {
            return (p.mouvement(xp + 1, yp) && p.pices_in_the_way(xp + 1, yp) && p.ownPieceK(xp + 1, yp)) || (p.mouvement(xp, yp - 1) && p.pices_in_the_way(xp, yp - 1) && p.ownPieceK(xp, yp - 1));
        } else if (xp == 7 && yp == 0) {
            return (p.mouvement(xp - 1, yp) && p.pices_in_the_way(xp - 1, yp) && p.ownPieceK(xp - 1, yp)) || (p.mouvement(xp, yp + 1) && p.pices_in_the_way(xp, yp + 1) && p.ownPieceK(xp, yp + 1));
        } else if (xp == 7 && yp == 7) {
            return (p.mouvement(xp - 1, yp) && p.pices_in_the_way(xp - 1, yp) && p.ownPieceK(xp - 1, yp)) || (p.mouvement(xp, yp - 1) && p.pices_in_the_way(xp, yp - 1) && p.ownPieceK(xp, yp - 1));
        } else if (xp == 0) {
            return (p.mouvement(xp + 1, yp) && p.pices_in_the_way(xp + 1, yp) && p.ownPieceK(xp + 1, yp));
        } else if (xp == 7) {
            return (p.mouvement(xp - 1, yp) && p.pices_in_the_way(xp - 1, yp) && p.ownPieceK(xp - 1, yp));
        } else if (yp == 0) {
            return (p.mouvement(xp, yp + 1) && p.pices_in_the_way(xp, yp + 1) && p.ownPieceK(xp, yp + 1));
        } else if (yp == 7) {
            return (p.mouvement(xp, yp - 1) && p.pices_in_the_way(xp, yp - 1) && p.ownPieceK(xp, yp - 1));
        } else {
            return (p.mouvement(xp, yp + 1) && p.pices_in_the_way(xp, yp + 1) && p.ownPieceK(xp, yp + 1)) || (p.mouvement(xp + 1, yp) && p.pices_in_the_way(xp + 1, yp) && p.ownPieceK(xp + 1, yp)) || (p.mouvement(xp - 1, yp) && p.pices_in_the_way(xp - 1, yp) && p.ownPieceK(xp - 1, yp)) || (p.mouvement(xp, yp - 1) && p.pices_in_the_way(xp, yp - 1) && p.ownPieceK(xp, yp - 1));
        }
    }

    public static void winnier(Player player) {
        DAOPlayer.newScore(player.getName());
        JOptionPane.showMessageDialog(null, "player " + player.getName() + " won");

    }
}
