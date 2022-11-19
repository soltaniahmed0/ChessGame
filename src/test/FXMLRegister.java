package test;

import DAO.DAOPlayer;
import Players.Player;
import chess.ChessGame;
import static chess.ChessGame.start;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author alaam
 */
public class FXMLRegister implements Initializable {

    @FXML
    private Button leaderBoard;
    @FXML
    private Button register2;
    @FXML
    private Button register1;
    @FXML
    private TextField player1;
    @FXML
    private TextField player2;
    @FXML
    private Button showBoard;
    private ToggleGroup C1;
    private ToggleGroup C2;
    @FXML
    private Button change;
    @FXML
    private Label color1;
    @FXML
    private Label color2;
    Player pl1 = new Player("");
    Player pl2 = new Player("");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showBoard.setDisable(true);

    }

    private void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource() == register1) {
            register_1();
        } else if (event.getSource() == register2) {
            register_2();
        } else if (event.getSource() == leaderBoard) {
            gotoLeaderBoard();
        } else if (event.getSource() == showBoard) {
            startGame();
        } else if (event.getSource() == change) {
            changecolor();
        }

    }

    @FXML
    public void register_1() {
        DAOPlayer DAO = new DAOPlayer();
        if (player1.getText().isEmpty()) {
            System.out.println("iktib player name");
            JOptionPane.showMessageDialog(null, "insert player 1 name pls");
        } else {
            pl1.setName(player1.getText());
            if (DAOPlayer.add(pl1)) {

                System.out.println("ok");

            } else {
                pl1.setScore(DAOPlayer.score(pl1.getName()));
                System.out.println(DAOPlayer.score(pl1.getName()));
            }

            change.setDisable(true);

            player1.setDisable(true);
            register1.setDisable(true);
            register1.setText("Registered");
            pl1.setColor(color1.getText());
            System.out.println(pl1.getColor());

            if (register1.getText().equals("Registered") && register2.getText().equals("Registered")) {
                showBoard.setDisable(false);

            }
        }

    }

    @FXML
    public void register_2() {
        if (player2.getText().isEmpty()) {
            System.out.println("iktib player name");
            JOptionPane.showMessageDialog(null, "insert player 2 name pls");
        } else {
            pl2.setName(player2.getText());

            if (DAOPlayer.add(pl2)) {
                System.out.println("ok");

            } else {
                pl2.setScore(DAOPlayer.score(pl2.getName()));
                System.out.println(DAOPlayer.score(pl2.getName()));
            }

            change.setDisable(true);

            player2.setDisable(true);
            register2.setDisable(true);
            register2.setText("Registered");
            pl2.setColor(color2.getText());
            System.out.println(pl2.getColor());

            if (register1.getText().equals("Registered") && register2.getText().equals("Registered")) {
                showBoard.setDisable(false);

            }
        }

    }

    @FXML
    public void gotoLeaderBoard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("leaderBoard.fxml"));
            Parent root = loader.load();

            LeaderBoardController leaderscene;
            leaderscene = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

    }

    @FXML
    private void changecolor() {
        if (color1.getText().equals("White")) {
            color1.setText("Black");
            color2.setText("White");
        } else {
            color1.setText("White");
            color2.setText("Black");
        }
    }

    @FXML
    private void startGame() throws IOException {

        Stage stage = (Stage) showBoard.getScene().getWindow();
        stage.hide();
        ChessGame.start(pl1, pl2);

    }
}
