package ir.ac.kntu.objects;

import ir.ac.kntu.constants.Keeper;
import ir.ac.kntu.objects.drug.Capsule;
import ir.ac.kntu.objects.drug.pill.Normal;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class GameObject implements Serializable {
    private int number;

    private Image image;

    private Image[] pillImages = new Image[3];

    private ImageView imageView = new ImageView();

    private int rowIndex;

    private int columnIndex;

    private String type;

    public GameObject(int number, int rowIndex, int columnIndex, String type) {
        this.number = number;

        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.type = type;
        setPillImages();
    }

    public void fall(String[][] map,ArrayList<ArrayList<GameObject>> gameObjects) {
        gameObjects.get(this.getRowIndex()).remove(this.getColumnIndex());
        gameObjects.get(this.getRowIndex()).add(this.getColumnIndex(),
                new Normal(Keeper.getInstance().getObjectNumber(), this.getRowIndex(), this.getColumnIndex()));
        map[this.getRowIndex()][this.getColumnIndex()] = ("n");
        this.moveY(1);
        gameObjects.get(this.getRowIndex()).remove(this.getColumnIndex());
        gameObjects.get(this.getRowIndex()).add(this.getColumnIndex(), this);
        //System.out.println(gameObjects.get(i.getRowIndex()).get(i.getColumnIndex()).getType());
        map[this.getRowIndex()][this.getColumnIndex()] = this.getType();
        /*System.out.println("################");
           System.out.print(i.getRowIndex()+"   ");
           System.out.print(i.getColumnIndex()+"   ");
           System.out.println(map[i.getRowIndex()][i.getColumnIndex()]);*/
    }


    public void setPillImages() {
        try {
            pillImages[0]=(new Image(new FileInputStream("src/main/resources/mapObjects/pills/blue.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            pillImages[1]=(new Image(new FileInputStream("src/main/resources/mapObjects/pills/red.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            pillImages[2]=(new Image(new FileInputStream("src/main/resources/mapObjects/pills/yellow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setRowAndColumn(int columnIndex, int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public void moveX(int move) {
        columnIndex+=move;
    }

    public void moveY(int move) {
        rowIndex+=move;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        if(type.equals("b1")){
            setImage(pillImages[0]);
        }else if(type.equals("r1")){
            setImage(pillImages[1]);
        }else if(type.equals("y1")){
            setImage(pillImages[2]);
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setImage(Image image){
        this.image=image;
        this.imageView.setImage(image);
    }

    public Image getImage(){
        return this.image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        GameObject that = (GameObject) o;
        return number == that.number && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }
}
