package ir.ac.kntu.saving;

import ir.ac.kntu.map.MapParser;
import ir.ac.kntu.objects.CapsuleSituation;
import ir.ac.kntu.objects.GameObject;
import ir.ac.kntu.objects.drug.Capsule;
import ir.ac.kntu.util.RandomHelper;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    private ArrayList<PlayerInfo> players=new ArrayList<>();

    private PlayerInfo playerInfo;

    private String[] colors={"r","b","y"};

    private String[][] map;

    private MapParser mapParser=new MapParser();

    private ArrayList<ArrayList<GameObject>> gameObjects;

    private boolean end=false;

    private int virusNumber;

    private int score=0;

    private int topScore;

    private Label topText=new Label();

    private Label levelText=new Label();

    private Label speedText=new Label();

    private Capsule nextCapsule;

    private Capsule currentCapsule=null;

    private ArrayList<Capsule> pillsOfCapsules=new ArrayList<>();

    private CapsuleSituation capSituation;

    private ImageView nextCapsuleImage1=new ImageView();

    private ImageView nextCapsuleImage2=new ImageView();

    public GameInfo(String[][] map, ArrayList<ArrayList<GameObject>> gameObjects, boolean end,
                    int virusNumber, int score, Label topText, Label levelText, Label speedText,
                    Capsule nextCapsule, Capsule currentCapsule,
                    ImageView nextCapsuleImage1,ImageView nextCapsuleImage2, CapsuleSituation capSituation) {
        this.map = map;
        this.gameObjects = gameObjects;
        this.end = end;
        this.virusNumber = virusNumber;
        this.score = score;
        this.topText = topText;
        this.levelText = levelText;
        this.speedText = speedText;
        this.nextCapsule = nextCapsule;
        this.currentCapsule = currentCapsule;
        this.nextCapsuleImage1 = nextCapsuleImage1;
        this.nextCapsuleImage2= nextCapsuleImage2;
        this.capSituation = capSituation;
    }

    public Capsule getNextCapsule() {
        return nextCapsule;
    }

    public Capsule getCurrentCapsule() {
        return currentCapsule;
    }

    public ArrayList<PlayerInfo> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerInfo> players) {
        this.players = players;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public MapParser getMapParser() {
        return mapParser;
    }

    public void setMapParser(MapParser mapParser) {
        this.mapParser = mapParser;
    }

    public ArrayList<ArrayList<GameObject>> getGameObjects() {
        return gameObjects;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public void setNextCapsule(Capsule nextCapsule) {
        this.nextCapsule = nextCapsule;
    }

    public void setCurrentCapsule(Capsule currentCapsule) {
        this.currentCapsule = currentCapsule;
    }

    public ArrayList<Capsule> getPillsOfCapsules() {
        return pillsOfCapsules;
    }

    public void setPillsOfCapsules(ArrayList<Capsule> pillsOfCapsules) {
        this.pillsOfCapsules = pillsOfCapsules;
    }

    public ImageView getNextCapsuleImage1() {
        return nextCapsuleImage1;
    }

    public void setNextCapsuleImage1(ImageView nextCapsuleImage1) {
        this.nextCapsuleImage1 = nextCapsuleImage1;
    }

    public ImageView getNextCapsuleImage2() {
        return nextCapsuleImage2;
    }

    public void setNextCapsuleImage2(ImageView nextCapsuleImage2) {
        this.nextCapsuleImage2 = nextCapsuleImage2;
    }

    public String[][] getMap() {
        return map;
    }

    public void setMap(String[][] map) {
        this.map = map;
    }

    public void setGameObjects(ArrayList<ArrayList<GameObject>> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getVirusNumber() {
        return virusNumber;
    }

    public void setVirusNumber(int virusNumber) {
        this.virusNumber = virusNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Label getTopText() {
        return topText;
    }

    public void setTopText(Label topText) {
        this.topText = topText;
    }

    public Label getLevelText() {
        return levelText;
    }

    public void setLevelText(Label levelText) {
        this.levelText = levelText;
    }

    public Label getSpeedText() {
        return speedText;
    }

    public void setSpeedText(Label speedText) {
        this.speedText = speedText;
    }

    public CapsuleSituation getCapSituation() {
        return capSituation;
    }

    public void setCapSituation(CapsuleSituation capSituation) {
        this.capSituation = capSituation;
    }
}
