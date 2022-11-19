/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Players.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOPlayer {

    public static boolean add(Player e) {
        Connection cn = (Connection) connexion.Connecter();
        String query = "insert into players (name,score,rank) values (?,0,0)";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, e.getName());

            int n = ps.executeUpdate();
            if (n >= 1) {
                System.out.println("succes add");
                return true;
            } else {
                System.out.println("didn't insert");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return false;
    }

    public static ObservableList<Player> list() {
        ObservableList<Player> players = FXCollections.observableArrayList();
        Connection cn = (Connection) connexion.Connecter();
        String requete = "select * from `players`;";
        int score, rank;
        String name;
        Player p;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                name = rs.getString(1);
                score = rs.getInt(2);
                rank = rs.getInt(3);

                p = new Player(name);
                p.setRank(rank);
                p.setScore(score);
                players.add(p);
            }
        } catch (SQLException e) {

            System.out.println(e.getCause());
        }

        return players;
    }

    public static int score(String name) {
        Connection cn = (Connection) connexion.Connecter();
        String query = "select score from players where name =?";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("score");

            }
        } catch (SQLException ex) {
            System.out.println("error in the query");
        }
        return 0;
    }

    public static void newScore(String name) {
        Connection cn = (Connection) connexion.Connecter();
        String query = "update players set score= score +1 where name= ?";
        try {
            PreparedStatement ps = cn.prepareStatement(query);

            ps.setString(1, name);
            int n = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void putRank(int rank, String name) {
        Connection cn = (Connection) connexion.Connecter();
        String query = "update players set rank=? where name=? ";
        try {
            PreparedStatement ps = cn.prepareStatement(query);
            ps.setInt(1, rank);
            ps.setString(2, name);
            int n = ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
