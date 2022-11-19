/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connexion {

    private static Connection conn;
    private static String user;
    private static String passWord;

    public static Connection Connecter() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/chessgame", user, passWord);
                System.out.println("Good connection");

            } catch (ClassNotFoundException e) {
                System.out.println("driver not found " + e.getMessage());
            } catch (SQLException e) {
                System.out.println("data base not found " + e.getMessage());
            }

        }
        return conn;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setpassWord(String passWord) {
        this.passWord = passWord;
    }
}
