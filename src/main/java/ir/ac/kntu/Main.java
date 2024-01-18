package ir.ac.kntu;

import ir.ac.kntu.menu.FirstMenu;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.saving.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Sina Rostami
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root=new Pane();
        Scene scene = new Scene(root, 800, 600, Color.GREEN);
        root.getStyleClass().add("root");
        try {
            root.getStylesheets().add(
                    this.getClass()
                            .getResource("/style.css")
                            .toString());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        FirstMenu menu=new FirstMenu(primaryStage,scene,root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dr-mario");
        primaryStage.show();
    }

}