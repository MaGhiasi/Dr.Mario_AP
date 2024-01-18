package ir.ac.kntu.util;

import ir.ac.kntu.StartGame;
import ir.ac.kntu.animation.BlueVirusGame;
import ir.ac.kntu.animation.RedVirusGame;
import ir.ac.kntu.animation.YellowVirusGame;
import ir.ac.kntu.menu.FirstMenu;
import ir.ac.kntu.saving.PlayerInfo;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class GameHelper {

    @NotNull
    public static Background getBackground(File file) {
        BackgroundFill backgroundFill= null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

    /*public static void printMap(String[][] map) {
        System.out.println("map");
        for (int i=0;i< map.length;i++){
            for(int j=0;j<map[0].length;j++){
                System.out.print(map[i][j]+"   ");
            }
            System.out.print("\n");
        }
    }*/

    public static void addNodesToPane(Pane root, Pane board, Button stop, Label scoreText, ImageView nextCapsuleImage1,
                                      ImageView nextCapsuleImage2, Label topText, Label levelText, Label virusText, Label speedText) {
        scoreText.setLayoutX(120);
        scoreText.setLayoutY(220);
        nextCapsuleImage1.setFitHeight(25);
        nextCapsuleImage1.setFitWidth(25);
        nextCapsuleImage2.setFitHeight(25);
        nextCapsuleImage2.setFitWidth(25);
        topText.setLayoutX(120);
        topText.setLayoutY(145);
        levelText.setLayoutX(630);
        levelText.setLayoutY(380);
        speedText.setLayoutX(630);
        speedText.setLayoutY(460);
        virusText.setLayoutX(630);
        virusText.setLayoutY(530);
        board.setLayoutX(300);
        board.setLayoutY(186);
        nextCapsuleImage1.setLayoutX(600);
        nextCapsuleImage1.setLayoutY(200);
        nextCapsuleImage2.setLayoutX(625);
        nextCapsuleImage2.setLayoutY(200);
        stop.setLayoutX(110);
        stop.setLayoutY(20);
    }

    public static void setAnimatedVirus(Pane root) {
        YellowVirusGame y1=new YellowVirusGame();
        RedVirusGame r1=new RedVirusGame();
        BlueVirusGame b1=new BlueVirusGame();
        y1.getImageView().setFitHeight(50);
        y1.getImageView().setFitWidth(50);
        r1.getImageView().setFitHeight(50);
        r1.getImageView().setFitWidth(50);
        b1.getImageView().setFitHeight(50);
        b1.getImageView().setFitWidth(50);
        root.getChildren().addAll(y1.getImageView(),r1.getImageView(),b1.getImageView());
        b1.getImageView().setLayoutX(150);
        b1.getImageView().setLayoutY(370);
        y1.getImageView().setLayoutX(80);
        y1.getImageView().setLayoutY(370);
        r1.getImageView().setLayoutX(110);
        r1.getImageView().setLayoutY(450);
    }

    public static void gameOver(Pane root) {
        root.getChildren().clear();
        ImageView imageView=new ImageView();
        Image image=null;
        try {
            image=(new Image(new FileInputStream("src/main/resources/images/GameOver.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);
        root.getChildren().add(imageView);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setLayoutY(150);
        imageView.setLayoutX(150);
        Timeline timeline1= new Timeline(new KeyFrame(Duration.millis(1000), e->gameOver1(root,0)));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }

    public static void gameOver1(Pane root,int i) {
        root.getChildren().clear();
        ImageView imageView=new ImageView();
        Image image=null;
        try {
            image=(new Image(new FileInputStream("src/main/resources/images/GameOver.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);
        root.getChildren().add(imageView);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setLayoutY(150);
        imageView.setLayoutX(150);
        i++;

    }

    public static void showRanks(Stage stage, Scene scene, Pane root,ArrayList<PlayerInfo> players) {
        root.getChildren().clear();
        ListView listView=new ListView();
        listView.getItems().addAll(players);
        listView.getStyleClass().add("listView");
        listView.setPrefHeight(250);
        listView.setPrefWidth(600);
        root.getChildren().add(listView);
        listView.setLayoutX(50);
        listView.setLayoutY(50);
        Button back=new Button("BACK");
        back.setOnAction(e->{
            FirstMenu firstMenu=new FirstMenu(stage,scene,root);
        });
        root.getChildren().add(back);
        back.setLayoutX(50);
        back.setLayoutY(420);
    }
}
