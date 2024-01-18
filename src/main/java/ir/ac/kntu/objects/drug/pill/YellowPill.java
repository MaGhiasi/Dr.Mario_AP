package ir.ac.kntu.objects.drug.pill;

import ir.ac.kntu.objects.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YellowPill extends GameObject implements Pill{
    public YellowPill(int num,int rowIndex,int columnIndex){
        super(num,rowIndex,columnIndex,"y1");
        try {
            setImage(new Image(new FileInputStream("src/main/resources/mapObjects/pills/yellow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getImageView().setFitHeight(22);
        getImageView().setFitWidth(25);
    }
}
