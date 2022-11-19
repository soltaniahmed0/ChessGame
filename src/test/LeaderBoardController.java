/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import DAO.DAOPlayer;
import Players.Player;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author alaam
 */
public class LeaderBoardController implements Initializable {

    @FXML
    private TableView<Player> leaderboard;
    @FXML
    private TableColumn<Player, String> player;
    @FXML
    private TableColumn<Player, Integer> Score;
    @FXML
    private TableColumn<Player, Integer> Rank;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showLeaderBoard();
    }

    public void showLeaderBoard() {

        Comparator<Player> PlayerComparateur = Comparator.comparing(Player::getScore);
        ObservableList<Player> players = DAOPlayer.list();
        PlayerComparateur.reversed();
        players.sort(PlayerComparateur);
        FXCollections.reverse(players);

        int i = 1;
        Player p_1 = new Player();
        p_1.setScore(0);
        for (Player p : players) {

            if (p_1.getScore() == p.getScore()) {
                p.setRank(p_1.getRank());
                DAOPlayer.putRank(p_1.getRank(), p.getName());
                i++;
            } else {
                p.setRank(i);
                DAOPlayer.putRank(i, p.getName());
                i++;
            }
            p_1 = p;
        }
        player.setCellValueFactory(new PropertyValueFactory<>("name"));
        Score.setCellValueFactory(new PropertyValueFactory<>("score"));

        Rank.setCellValueFactory(new PropertyValueFactory<>("rank"));

        leaderboard.setItems(players);

    }

}
