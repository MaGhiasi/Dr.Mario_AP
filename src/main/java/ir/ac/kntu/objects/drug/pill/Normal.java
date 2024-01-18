package ir.ac.kntu.objects.drug.pill;

import ir.ac.kntu.objects.GameObject;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Normal extends GameObject {
    public Normal(int num,int rowIndex, int columnIndex) {
        super(num,rowIndex, columnIndex,"n");
        try {
            setImage(new Image(new FileInputStream("src/main/resources/mapObjects/normal.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getImageView().setFitHeight(22);
        getImageView().setFitWidth(25);
    }
}
