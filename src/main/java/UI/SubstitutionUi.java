package UI;

import Model.Player;
import Service.GameService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

    public class SubstitutionUi {

        public Parent getView(GameService gameService) {
            Label title = new Label("Substitution");
            Label subsLabel = new Label(
                    "Subs: " + gameService.getSubsCount() + " / " + gameService.getMaxSubs()
            );

            ObservableList<Player> squadList = FXCollections.observableArrayList();
            squadList.addAll(gameService.getCurrentSquad());

            ObservableList<Player> benchList = FXCollections.observableArrayList();
            benchList.addAll(gameService.getCurrentBench());

            ListView<Player> squadView = new ListView<>(squadList);
            if (gameService.getInjuredPlayer() != null) {
                squadView.getSelectionModel().select(gameService.getInjuredPlayer());
            }
            ListView<Player> benchView = new ListView<>(benchList);

                    Button subBtn = new Button("Substitute");
                    Button backBtn = new Button("Back to Match");
                    Styles.styleButton(subBtn);
                    Styles.styleButton(backBtn);
                    subBtn.setDisable(!gameService.canSubs());

                    subBtn.setOnAction(e -> {
                        Player outPlayer = squadView.getSelectionModel().getSelectedItem();
                        Player inPlayer = benchView.getSelectionModel().getSelectedItem();

                        if (outPlayer != null && inPlayer != null && gameService.canSubs()) {
                            squadList.remove(outPlayer);
                            benchList.remove(inPlayer);

                            squadList.add(inPlayer);
                            benchList.add(outPlayer);

                            gameService.getCurrentSquad().clear();
                            gameService.getCurrentSquad().addAll(squadList);

                            gameService.getCurrentBench().clear();
                            gameService.getCurrentBench().addAll(benchList);
                            gameService.increaseSubsCount();
                            if (!gameService.canSubs()) {
                                subBtn.setDisable(true);
                            }
                            subsLabel.setText(
                                    "Subs: " + gameService.getSubsCount() + " / " + gameService.getMaxSubs()
                                            );
                        }
                    });

                    backBtn.setOnAction(e -> {
                        Navigator.navigate(ViewType.MATCHPLAY);
                    });

                    VBox leftBox = new VBox(10, new Label("On Field"), squadView);
                    VBox rightBox = new VBox(10, new Label("Bench"), benchView);

                    HBox lists = new HBox(20, leftBox, rightBox);
                    lists.setAlignment(Pos.CENTER);

                    HBox buttons = new HBox(15, subBtn, backBtn);
                    buttons.setAlignment(Pos.CENTER);

                    VBox root = new VBox(20, title,subsLabel, lists, buttons);
                    root.setAlignment(Pos.CENTER);
                    root.setPadding(new Insets(20));

                    return root;
                }
            }