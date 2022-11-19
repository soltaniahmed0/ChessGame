/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import DAO.connexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

public class lunch extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            connexion c = new connexion();
            c.setUser("root");
            c.setpassWord("ahmedsoltani");
            Connection Connecter = connexion.Connecter();
            Parent root;
            root = FXMLLoader.load(getClass().getResource("Register1.fxml"));
            Scene scene = new Scene(root);
            scene.setRoot(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
