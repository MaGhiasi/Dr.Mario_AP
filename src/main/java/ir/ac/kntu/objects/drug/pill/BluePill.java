package ir.ac.kntu.objects.drug.pill;

import ir.ac.kntu.objects.GameObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BluePill extends GameObject implements Pill{
    public BluePill(int num,int rowIndex,int columnIndex){
        super(num,rowIndex,columnIndex,"b1");
        try {
            setImage(new Image(new FileInputStream("src/main/resources/mapObjects/pills/blue.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getImageView().setFitHeight(22);
        getImageView().setFitWidth(25);
    }
}
