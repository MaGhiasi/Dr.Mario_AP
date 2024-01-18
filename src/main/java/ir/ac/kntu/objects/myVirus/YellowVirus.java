package ir.ac.kntu.objects.myVirus;

import ir.ac.kntu.objects.GameObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class YellowVirus extends GameObject implements Virus{
    private Image[] images=new Image[2];

    public YellowVirus(int num,int rowIndex, int columnIndex) {
        super(num,rowIndex,columnIndex,"y");
        try {
            images[0]=(new Image(new FileInputStream("src/main/resources/mapObjects/yellowVirus.png")));
            setImage(images[0]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[1]=new Image(new FileInputStream("src/main/resources/mapObjects/yellowVirus2.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        getImageView().setFitHeight(22);
        getImageView().setFitWidth(25);
        run();
    }

    private void run() {
        final int pause = 120;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(pause), e->nextFrame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void nextFrame() {
        if(getImage().equals(images[0])){
            setImage(images[1]);
        }else {
            setImage(images[0]);
        }
    }

}
