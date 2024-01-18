package ir.ac.kntu.animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BlueVirusGame {
    private Image image;

    private Image[] images=new Image[6];

    private ImageView imageView=new ImageView();

    public BlueVirusGame() {
        try {
            images[0]=(new Image(new FileInputStream("src/main/resources/animation/blue/blue1.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[1]=new Image(new FileInputStream("src/main/resources/animation/blue/blue2.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[2]=new Image(new FileInputStream("src/main/resources/animation/blue/blue3.png"));
            setImage(images[2]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[3]=new Image(new FileInputStream("src/main/resources/animation/blue/blue4.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[4]=new Image(new FileInputStream("src/main/resources/animation/blue/blue5.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            images[5]=new Image(new FileInputStream("src/main/resources/animation/blue/blue6.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImage(image);
        run();
    }

    private void run() {
        final int pause = 600;
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(pause), e->nextFrame()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        System.out.println("in run");
    }

    private void nextFrame() {
        if(imageView.getImage().equals(images[0])){
            imageView.setImage(images[1]);
        }else if(imageView.getImage().equals(images[1])){
            imageView.setImage(images[2]);
        }else if(imageView.getImage().equals(images[2])){
            imageView.setImage(images[3]);
        }else if(imageView.getImage().equals(images[3])){
            imageView.setImage(images[4]);
        }else if(imageView.getImage().equals(images[4])){
            imageView.setImage(images[5]);
        }else if(imageView.getImage().equals(images[5])){
            imageView.setImage(images[0]);
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}
