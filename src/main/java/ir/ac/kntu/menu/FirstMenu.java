package ir.ac.kntu.menu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FirstMenu {
    public FirstMenu(Stage stage, Scene scene, Pane root) {
        drawMenu(stage, scene, root);

    }

    private void drawMenu(Stage stage, Scene scene, Pane mainPain) {
        mainPain.getChildren().clear();
        Background background = null;
        background = getBackground(new File("src/main/resources/firstMenuBack.jpg"));
        mainPain.setBackground(background);
        Button start = new Button("START");
        start.getStyleClass().add("start");
        start.setOnAction(e -> {
            Menu menu = new Menu(stage, scene, mainPain);
        });
        Button exit = new Button("EXIT");
        exit.setOnAction(e -> {
            stage.close();
        });
        mainPain.getStyleClass().add("root");
        start.getStyleClass().add("start");
        exit.getStyleClass().add("exit");
        mainPain.getChildren().add(exit);
        mainPain.getChildren().add(start);
        start.setLayoutX(200);
        start.setLayoutY(250);
        exit.setLayoutX(200);
        exit.setLayoutY(350);
    }

    @NotNull
    private Background getBackground(File file) {
        BackgroundFill backgroundFill = null;
        try {
            backgroundFill = new BackgroundFill(new ImagePattern(
                    new Image(new FileInputStream(file))), CornerRadii.EMPTY, Insets.EMPTY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(backgroundFill);
    }

}
