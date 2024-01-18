package ir.ac.kntu.menu;

import ir.ac.kntu.StartGame;
import ir.ac.kntu.saving.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menu {
    private Pane root;

    private Stage stage;

    private Scene scene;

    private PlayerSaver playerSaver=new BinaryPlayerSaver();

    private ArrayList<PlayerInfo> players=playerSaver.getAllPlayers();


    public Menu(Stage stage, Scene scene, Pane root){
        this.root=root;
        this.scene=scene;
        this.stage=stage;
        Background background = null;
        background = getBackground(new File("src/main/resources/background-menu.png"));
        root.setBackground(background);
        drawMenu();
    }

    @NotNull
    private Background getBackground(File file) {
        BackgroundFill backgroundFill= null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

    private void drawMenu() {
        ListView listView=new ListView();
        listView.getItems().addAll(players);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ArrayList<PlayerInfo> result=new ArrayList<>();
        listView.getStyleClass().add("listView");
        listView.setPrefHeight(250);
        listView.setPrefWidth(600);
        Button add=new Button("ADD PLAYER");
        Button back=new Button("BACK");
        Button submit=new Button("SUBMIT");
        root.getChildren().clear();
        root.getChildren().addAll(listView,add,submit,back);
        listView.setLayoutX(100);
        listView.setLayoutY(90);
        add.setLayoutX(130);
        add.setLayoutY(450);
        submit.setLayoutX(355);
        submit.setLayoutY(450);
        back.setLayoutX(535);
        back.setLayoutY(450);
        eventHandler(listView, result, add, back, submit);
    }

    private void eventHandler(ListView listView, ArrayList<PlayerInfo> result, Button add, Button back, Button submit) {
        listView.setOnMouseClicked(e->{
            if(result.size()>=1) {
                result.clear();
            }
            System.out.println("ppp");
            PlayerInfo playerInfo=(PlayerInfo) listView.getSelectionModel().getSelectedItem();
            if(playerInfo!=null){
                result.add(playerInfo);
            }
        });
        back.setOnAction(e->{
            FirstMenu firstMenu=new FirstMenu(stage,scene,root);
        });
        submit.setOnAction(e->{
            if(!result.get(0).isHaveUnfinishedGame()){
                System.out.println();
                setGameInfo(result);
            }else if(result.get(0).getGameInfo() !=null){
                StartGame startGame=new StartGame(stage,scene,root,players,result.get(0),result.get(0).getGameInfo());
            }
        });
        add.setOnAction(e->{
            addNewPlayer(listView);
        });
    }

    private void setGameInfo( ArrayList<PlayerInfo> result) {
        Background background = getBackground(new File("src/main/resources/speedLevelMenu.png"));
        root.setBackground(background);
        Label textLevel=new Label("1");
        textLevel.setFont(Font.font(30));
        Button levelUp=new Button("LEVEL UP");
        Button levelDown=new Button("LEVEL DOWN");
        Label textSpeed=new Label("SLOW");
        textSpeed.setFont(Font.font(30));
        Button slow=new Button("SLOW");
        Button medium=new Button("MEDIUM");
        Button fast=new Button("FAST");
        Button submit=new Button("Submit");
        root.getChildren().clear();
        root.getChildren().addAll(submit,fast,medium,slow,textSpeed,levelDown,levelUp,textLevel);
        gameInfoHandler(result, textLevel, levelUp, levelDown, textSpeed, slow, medium, fast, submit);
        fixPlace(textLevel, levelUp, levelDown, textSpeed, slow, medium, fast, submit);
    }

    private void fixPlace(Label textLevel, Button levelUp, Button levelDown, Label textSpeed, Button slow, Button medium, Button fast, Button submit) {
        textLevel.setLayoutX(120);
        textLevel.setLayoutY(220);
        levelUp.setLayoutX(220);
        levelUp.setLayoutY(220);
        levelDown.setLayoutX(355);
        levelDown.setLayoutY(220);
        textSpeed.setLayoutX(130);
        textSpeed.setLayoutY(380);
        slow.setLayoutX(250);
        slow.setLayoutY(380);
        medium.setLayoutX(380);
        medium.setLayoutY(380);
        fast.setLayoutX(530);
        fast.setLayoutY(380);
        submit.setLayoutX(150);
        submit.setLayoutY(450);
    }

    private void gameInfoHandler(ArrayList<PlayerInfo> result, Label textLevel, Button levelUp, Button levelDown, Label textSpeed, Button slow, Button medium, Button fast, Button submit) {
        levelUp.setOnAction(e1->{
            int level=Integer.parseInt(textLevel.getText());
            textLevel.setText(String.valueOf(level+1));
        });
        levelDown.setOnAction(e1->{
            int level=Integer.parseInt(textLevel.getText());
            if(level>1) {
                textLevel.setText(String.valueOf(level-1));
            }
        });
        slow.setOnAction(e->{
            textSpeed.setText("SLOW");
        });
        medium.setOnAction(e->{
            textSpeed.setText("MEDIUM");
        });
        fast.setOnAction(e->{
            textSpeed.setText("FAST");
        });
        submit.setOnAction(e->{
            StartGame gameLoop=new StartGame(stage,scene,root,players, result.get(0),
                    Integer.parseInt(textLevel.getText()), textSpeed.getText());
        });
    }

    private void addNewPlayer(ListView listView) {
        Stage stage=new Stage();
        Pane newPane=new Pane();
        Background background1 = getBackground(new File("src/main/resources/background-menu.png"));
        newPane.setBackground(background1);
        Label label=new Label("Name");
        label.setFont(new Font(50));
        TextField name=new TextField();
        Button save=new Button("Save");
        save.setOnAction(e->{
            if(!name.getText().isEmpty()) {
                PlayerInfo playerInfo=new PlayerInfo(name.getText());
                players.add(playerInfo);
                listView.getItems().add(playerInfo);
                stage.close();
            }
        });
        newPane.getChildren().addAll(label,name,save);
        label.setLayoutX(50);
        label.setLayoutY(50);
        name.setLayoutX(50);
        name.setLayoutY(155);
        save.setLayoutX(50);
        save.setLayoutY(250);
        stage.setTitle("add new player");
        stage.setScene(new Scene(newPane,350,350,Color.BLUE));
        stage.show();
    }

}
