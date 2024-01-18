package ir.ac.kntu;

import ir.ac.kntu.constants.Keeper;
import ir.ac.kntu.objects.Speed;
import ir.ac.kntu.saving.GameInfo;
import ir.ac.kntu.saving.PlayerInfo;
import javafx.animation.KeyFrame;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class StartGame {

    private Stage stage;

    private Pane root;

    private Scene scene;

    private int pause= Keeper.getInstance().loading*100;

    private Label theCount =new Label("5");

    private Timeline timeline1;

    private ArrayList<PlayerInfo> players=new ArrayList<>();

    private PlayerInfo playerInfo;

    private int level;

    private Speed speed;

    private GameInfo gameInfo;

    public StartGame(Stage stage, Scene scene,Pane root, ArrayList<PlayerInfo> players, PlayerInfo player,GameInfo gameInfo){
        this.stage=stage;
        this.root=root;
        this.scene=scene;
        this.playerInfo=player;
        this.players=players;
        this.gameInfo=gameInfo;
        firstCounting();
    }

    public StartGame(Stage stage, Scene scene,Pane root, ArrayList<PlayerInfo> players,
                     PlayerInfo player, int level, String speed){
        this.stage=stage;
        this.root=root;
        this.scene=scene;
        this.playerInfo=player;
        this.players=players;
        this.level=level;
        this.speed=Speed.valueOf(speed.toUpperCase());
        firstCounting();
    }

    private void firstCounting() {
        Background background=new Background(new BackgroundFill(Color.rgb(120,144,255),
                CornerRadii.EMPTY, Insets.EMPTY));
        root.setBackground(background);
        root.getChildren().clear();
        root.getChildren().add(theCount);
        theCount.setFont(Font.font(120));
        theCount.setLayoutX(350);
        theCount.setLayoutY(200);
        timeline1= new Timeline(new KeyFrame(Duration.millis(pause), e->counting()));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }

    private void counting() {
        theCount.setText(String.valueOf(Integer.parseInt(theCount.getText())-1));
        if(Integer.parseInt(theCount.getText())==0){
            timeline1.stop();
            next();
        }
    }

    private void next() {
        if(gameInfo==null) {
            GameLoop gameLoop = new GameLoop(stage, scene, root, players,
                        playerInfo, level, speed.toString());
        }else {
            GameLoop gameLoop=new GameLoop(stage,scene,root,players,playerInfo,gameInfo);
        }
    }
}
