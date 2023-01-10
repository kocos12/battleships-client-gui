package com.example.battleships_client_gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class MainPaneController {
    @FXML
    private GridPane battlegroundGrid;

    @FXML
    private TextField haslo;

    @FXML
    private TextField login;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginInfo;

    @FXML
    private Label matchmakingInfo;

    @FXML
    private Button startGameButton;
    @FXML
    private Label startInfoLabel;
    private Client client;

    public void initialize() {
        client = new Client("127.0.0.1",9000);
        client.runClient();

        logInPlayer();
        placeShipsOnBattleground();

        startGameButton.setOnAction(actionEvent -> {
            if(client.isLogged()){
                startInfoLabel.setText("");
                client.sendToServer("Ready");
                client.sendToServer("Ready");
                //matchmakingInfo.setText(client.getInfo());
                prepareGridForBattle();
            }else{
                startInfoLabel.setText("Zaloguj sie aby rozpoczac gre!");
            }
        });
    }
    private void prepareGridForBattle(){
        for (int i = 0; i <10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton battlegroundChunkButton = new ToggleButton("?");
                battlegroundChunkButton.setAlignment(Pos.CENTER);
                battlegroundChunkButton.setPrefSize(40,40);
                battlegroundChunkButton.setId(String.valueOf(i) + j);

                battlegroundChunkButton.setOnAction(actionEvent -> {
                    if(client.isLogged()){
                        if (battlegroundChunkButton.isSelected()) {
                            String answer = client.shoot(battlegroundChunkButton.getId());
                            battlegroundChunkButton.setText(answer);
                        }
                    }else{
                        startInfoLabel.setText("Zaloguj sie aby rozpoczac gre!");
                    }
                });
                battlegroundGrid.add(battlegroundChunkButton, i, j);
            }
        }
    }
    private void placeShipsOnBattleground() {
        for (int i = 0; i <10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton battlegroundChunkButton = new ToggleButton("~");
                battlegroundChunkButton.setAlignment(Pos.CENTER);
                battlegroundChunkButton.setPrefSize(40,40);
                battlegroundChunkButton.setId(String.valueOf(i) + j);

                battlegroundChunkButton.setOnAction(actionEvent -> {
                    if(client.isLogged()){
                        if (battlegroundChunkButton.isSelected()) {
                            battlegroundChunkButton.setText("O");
                            client.sendToServer("add");
                            client.sendToServer(battlegroundChunkButton.getId());
                        } else {
                            battlegroundChunkButton.setText("~");
                            client.sendToServer("remove");
                            client.sendToServer(battlegroundChunkButton.getId());
                        }
                    }else{
                        startInfoLabel.setText("Zaloguj sie aby rozpoczac gre!");
                    }
                });
                battlegroundGrid.add(battlegroundChunkButton, i, j);
            }
        }
    }

    private void logInPlayer() {
        loginButton.setOnAction(actionEvent -> {
            String confirmLogIn = client.logInClient(login.getText(), haslo.getText());
            loginInfo.setText(confirmLogIn);
            matchmakingInfo.setText(client.getInfo());
        });
    }

}
