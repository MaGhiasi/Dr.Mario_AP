package ir.ac.kntu;

import ir.ac.kntu.constants.Keeper;
import ir.ac.kntu.keyboard.KeyLogger;
import ir.ac.kntu.map.*;
import ir.ac.kntu.menu.FirstMenu;
import ir.ac.kntu.objects.CapsuleSituation;
import ir.ac.kntu.objects.GameObject;
import ir.ac.kntu.objects.drug.Capsule;
import ir.ac.kntu.objects.drug.pill.*;
import ir.ac.kntu.objects.myVirus.Virus;
import ir.ac.kntu.saving.*;
import ir.ac.kntu.util.*;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GameLoop {
    private Stage stage;

    private Pane root;

    private Scene scene;

    private ArrayList<PlayerInfo> players=new ArrayList<>();

    private PlayerInfo playerInfo;

    private Pane board=new Pane();

    private String[] colors={"r","b","y"};

    private String[][] map;

    private ArrayList<ArrayList<GameObject>> gameObjects;

    private boolean end=false;

    public double coefficient=1;

    private int virusNumber;

    private int score=0;

    private int topScore;

    private static int pause=1000;

    private Label topText=new Label(String.valueOf(topScore));

    private Label scoreText=new Label(String.valueOf(score));

    private Label levelText=new Label("LEVEL");

    private Label speedText=new Label("SPEED");

    private Label virusText=new Label(String.valueOf(virusNumber));

    private Button stop=new Button("STOP");

    private Capsule nextCapsule=new Capsule(RandomHelper.randomInt(6));

    private Capsule currentCapsule=null;

    private ArrayList<Capsule> pillsOfCapsules=new ArrayList<>();

    private ArrayList<GameObject> canFall=new ArrayList<>();

    private CapsuleSituation capSituation=CapsuleSituation.NOT_SPECIFIC;

    private ImageView nextCapsuleImage1=new ImageView();

    private ImageView nextCapsuleImage2=new ImageView();

    private Timeline timeline1;

    private Timeline timeline2;

    private Timeline timeline3;

    public GameLoop(Stage stage, Scene scene,Pane root,ArrayList<PlayerInfo> players, PlayerInfo player,
                    GameInfo gameInfo){
        this.stage=stage;
        this.root=root;
        this.scene=scene;
        this.end=gameInfo.isEnd();
        KeyLogger keyLogger=new KeyLogger(scene,this);
        setScore(gameInfo.getScore());
        setTopScore(gameInfo.getTopScore());
        this.playerInfo=player;
        this.players=players;
        setVirusNumber(gameInfo.getVirusNumber());
        this.nextCapsule=gameInfo.getNextCapsule();
        this.currentCapsule=gameInfo.getCurrentCapsule();
        this.levelText.setText(gameInfo.getLevelText().getText());
        this.speedText.setText(gameInfo.getSpeedText().getText());
        gameObjects=gameInfo.getGameObjects();
        speedPause();
        map=gameInfo.getMap();
        setTopScore(playerInfo.getHighScore());
        addInformationTORoot();
        addObjectsToBoard();
        startCapsuleFalling();
    }

    public GameLoop(Stage stage, Scene scene,Pane root,ArrayList<PlayerInfo> players, PlayerInfo player,int level,String speed){
        this.stage=stage;
        this.root=root;
        this.scene=scene;
        //setTopScore(playerInfo.getHighScore());
        KeyLogger keyLogger=new KeyLogger(scene,this);
        this.playerInfo=player;
        this.players=players;
        this.virusNumber=level*4;
        this.topScore=playerInfo.getHighScore();
        this.levelText.setText(String.valueOf(level));
        this.speedText.setText(speed.toUpperCase());
        this.virusText.setText(String.valueOf(virusNumber));
        speedPause();
        map=(new RandomMap()).makeMap(virusNumber,Keeper.getInstance().boardHeight, Keeper.getInstance().boardWidth);
        gameObjects=(new MapParser()).gameObjects(map);
        addInformationTORoot();
        addObjectsToBoard();
        startEachCapsule();
    }

    private void speedPause() {
        if(speedText.getText().equals("MEDIUM")){
            coefficient=Keeper.getInstance().normalCoefficient;
            pause*=1/coefficient;
        }else if(speedText.getText().equals("FAST")){
            coefficient=Keeper.getInstance().fastCoefficient;
            pause*=1/coefficient;
        }
    }

    private void startEachCapsule() {
        timeline1= new Timeline(new KeyFrame(Duration.millis(pause), e->mainLoop()));
        timeline1.setCycleCount(Timeline.INDEFINITE);
        timeline1.play();
    }

    private void startCapsuleFalling() {
        timeline2= new Timeline(new KeyFrame(Duration.millis(pause), e->mainLoop2()));
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();
    }

    private void startCheckDelete() {
        timeline3= new Timeline(new KeyFrame(Duration.millis(pause), e->mainLoop3()));
        timeline3.setCycleCount(Timeline.INDEFINITE);
        timeline3.play();
    }

    private void mainLoop() {
        if (end) {
            System.out.println("end");
            endGame();
            return;
        }
        if(capSituation==CapsuleSituation.FALLING){
            timeline1.stop();
            timeline1=null;
            startCapsuleFalling();
            capsuleFalling();
        }
        if(capSituation==CapsuleSituation.START){
            capSituation=CapsuleSituation.FALLING;
        }else if(capSituation==CapsuleSituation.NOT_SPECIFIC){
            addCapsuleToMap();
        }
        if (virusNumber<=0){
            end=true;
        }
        addObjectsToBoard();
    }

    private void mainLoop2() {
        System.out.println("in loop 2");
        if (end) {
            System.out.println("end");
            endGame();
            return;
        }
        if(capSituation==CapsuleSituation.FALLING){
            capsuleFalling();
        }else if(capSituation==CapsuleSituation.STABLE){
            timeline2.stop();
            timeline2=null;
            startCheckDelete();
            check4SameColor();
        }
        if (virusNumber<=0){
            end=true;
        }
        addObjectsToBoard();
    }

    private void mainLoop3() {
        System.out.println("in loop 3");
        if (end) {
            System.out.println("end");
            endGame();
            return;
        }
        if(capSituation==CapsuleSituation.NOT_SPECIFIC){
            timeline3.stop();
            timeline3=null;
            startEachCapsule();
        }else if(capSituation==CapsuleSituation.STABLE){
            check4SameColor();
            countVirus();
        }
        if (virusNumber<=0){
            end=true;
        }
        addObjectsToBoard();
    }

    public void check4SameColor(){
        int p=0,four=0;
        for(int i=0;i< gameObjects.size();i++){
            ArrayList<GameObject> row=gameObjects.get(i);
            for(int j=0;j<4;j++){
                for(int m=0;m<3;m++) {
                    if (row.get(j).getType().contains(colors[m]) &&
                            row.get(j + 1).getType().contains(colors[m]) &&
                            row.get(j + 2).getType().contains(colors[m]) &&
                            row.get(j + 3).getType().contains(colors[m])) {
                        if(row.get(j)instanceof Pill || row.get(j+1)instanceof Pill ||
                                row.get(j+2)instanceof Pill || row.get(j+3)instanceof Pill ){
                            deleteCells(j,i,2);
                            p++;
                            break;
                        }
                    }
                }
            }
        }
        p = getP(p);
        if(p==0) {
            currentCapsule=null;
            capSituation = CapsuleSituation.NOT_SPECIFIC;
        }else {
            setScore(score+p*5*(int) (coefficient*10));
        }
    }

    private int getP(int p) {
        for(int i=0;i< gameObjects.get(0).size();i++){
            for(int j=0;j<gameObjects.size()-3;j++){
                for(int m=0;m<3;m++) {
                    if (gameObjects.get(j).get(i).getType().contains(colors[m]) &&
                            gameObjects.get(j+1).get(i).getType().contains(colors[m]) &&
                            gameObjects.get(j+2).get(i).getType().contains(colors[m]) &&
                            gameObjects.get(j+3).get(i).getType().contains(colors[m])) {
                        if(gameObjects.get(j).get(i) instanceof Pill|| gameObjects.get(j+1).get(i) instanceof Pill||
                                gameObjects.get(j+2).get(i) instanceof Pill|| gameObjects.get(j+3).get(i) instanceof Pill){
                            deleteCells(j,i,2);
                            p++;
                            break;
                        }
                    }
                }
            }
        }
        return p;
    }

    public void deleteCells(int firstRow,int firstColumn, int kind){
        if(kind==1){
            for(int i=0;i<4;i++){
                map[firstRow][firstColumn+i]="n";
                if (gameObjects.get(firstRow).get(firstColumn+i) instanceof Virus){
                    virusReduction();
                }
                gameObjects.get(firstRow).remove(firstColumn+i);
                gameObjects.get(firstRow).add(firstColumn+i, new Normal(Keeper.getInstance().getObjectNumber(),firstRow,firstColumn+i ));
            }
        }else {
            for(int i=0;i< 4;i++){
                map[firstRow+i][firstColumn]="n";
                gameObjects.get(firstRow+i).remove(firstColumn);
                gameObjects.get(firstRow+i).add(firstColumn, new Normal(Keeper.getInstance().getObjectNumber(),firstRow+i,firstColumn));
            }
        }
    }

    public void capsuleFalling(){
        if(currentCapsule.checkCanMoveDown(map)){
            currentCapsule.moveDown(map,gameObjects);
        }else {
            capSituation=CapsuleSituation.STABLE;
        }
    }

    public void updateMap(Capsule capsule) {
        GameObject newObject=capsule.getPartOne();
        map[newObject.getRowIndex()][newObject.getColumnIndex()]=newObject.getType();
        newObject=capsule.getPartTwo();
        map[newObject.getRowIndex()][newObject.getColumnIndex()]=newObject.getType();
    }

    public void updateGameObjects(Capsule capsule) {
        GameObject newObject=capsule.getPartOne();
        gameObjects.get(newObject.getRowIndex()).remove(newObject.getColumnIndex());
        gameObjects.get(newObject.getRowIndex()).add(newObject.getColumnIndex(),newObject);
        newObject=capsule.getPartTwo();
        gameObjects.get(newObject.getRowIndex()).remove(newObject.getColumnIndex());
        gameObjects.get(newObject.getRowIndex()).add(newObject.getColumnIndex(),newObject);
    }

    private void addCapsuleToMap() {
        if(map[0][3].equals("n")&&map[0][4].equals("n")) {
            capSituation = CapsuleSituation.START;
            currentCapsule = nextCapsule;
            updateMap(currentCapsule);
            updateGameObjects(currentCapsule);
            pillsOfCapsules.add(currentCapsule);
            nextCapsule = null;
            nextCapsule = new Capsule(RandomHelper.randomInt(6));
            nextCapsuleImage1.setImage(nextCapsule.getPartOne().getImage());
            nextCapsuleImage2.setImage(nextCapsule.getPartTwo().getImage());
            System.out.println("addCapsuleToMap");
        }else {
            end=true;
        }
    }

    public void countVirus() {
        int p=0;
        for (int i=0;i<gameObjects.size();i++) {
            for (int j=0; j < gameObjects.get(i).size(); j++) {
                if(gameObjects.get(i).get(j) instanceof Virus){
                    p++;
                }
            }
        }
        setVirusNumber(p);
    }

    public void addObjectsToBoard() {
        board.getChildren().clear();
        for (int i=0;i<gameObjects.size();i++) {
            for (int j=0; j < gameObjects.get(i).size(); j++) {
                board.getChildren().add(gameObjects.get(i).get(j).getImageView());
                gameObjects.get(i).get(j).getImageView().setLayoutX(gameObjects.get(i).get(j).getColumnIndex() * 25);
                gameObjects.get(i).get(j).getImageView().setLayoutY((gameObjects.get(i).get(j).getRowIndex()) * 22);
            }
        }
    }

    private void addInformationTORoot() {
        Background background = GameHelper.getBackground(new File("src/main/resources/images/MainScene.png"));
        root.setBackground(background);
        Background background1=new Background(new BackgroundFill(Color.rgb(200,144,255),
                CornerRadii.EMPTY,Insets.EMPTY));
        board.setBackground(background1);
        root.getChildren().clear();
        stop.setFocusTraversable(false);
        root.getChildren().addAll(topText,scoreText,board,levelText,speedText);
        root.getChildren().addAll(virusText,stop,nextCapsuleImage1,nextCapsuleImage2);
        GameHelper.addNodesToPane(root,board,stop,scoreText,nextCapsuleImage1,nextCapsuleImage2,topText,
                levelText,virusText,speedText);
        gameInformationHandler();
        GameHelper.setAnimatedVirus(root);
    }

    private void gameInformationHandler() {
        stop.setStyle("-fx-border-radius: 40px ;\n" +
                "    -fx-background-color: rgba(1,255,0,0);\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-border-width: 0.5px;\n" +
                "    -fx-border-style: solid;\n" +
                "    -fx-width: 60px;\n" +
                "    -fx-font-family: 'Arial, Helvetica, sans-serif' ;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-border-color: rgba(120,144,225,50);");
        stop.setOnAction(e->{
            Timeline timeline;
            if(timeline1!=null){
                timeline=timeline1;
            }else if(timeline2!=null){
                timeline=timeline2;
            }else {
                timeline=timeline3;
            }
            timeline.pause();
            stopOrResume(timeline);
        });
    }

    private void stopOrResume(Timeline timeline) {
        Background background = GameHelper.getBackground(new File("src/main/resources/background.png"));
        Stage stage1=new Stage();
        GridPane gridPane=new GridPane();
        gridPane.setBackground(background);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setVgap(20);
        Button save=new Button("SAVE AND END");
        save.setOnAction(e->{
            GameInfo gameInfo=new GameInfo(map, gameObjects, end, virusNumber,score, topText,levelText, speedText,nextCapsule,currentCapsule, nextCapsuleImage1,nextCapsuleImage2, capSituation);
            saveAndUpdateThePlayer();
            stage1.close();
        });
        Button resume=new Button("RESUME");
        resume.setOnAction(e->{
            timeline.play();
            stage1.close();
        });
        gridPane.add(resume,0,1);
        gridPane.add(save,0,2);
        stage1.setTitle("save or resume");
        stage1.setScene(new Scene(gridPane,300,300,Color.BLUE));
        stage1.show();
    }

    public void endGame(){
        if(timeline1!=null){
            timeline1.stop();
        }else if(timeline2!=null){
            timeline2.stop();
        }else {
            timeline3.stop();
        }
        PlayerSaver playerSaver = new BinaryPlayerSaver();
        updateRanks();
        if(virusNumber==0) {
            playerSaver.saveAllPlayers(this.players);
            GameHelper.showRanks(stage, scene, root,players);
        }else if(virusNumber>0){
            playerSaver.saveAllPlayers(this.players);
            FirstMenu firstMenu =new FirstMenu(stage,scene,root);
            //StartGame startGame = new StartGame(stage, scene, root, players, playerInfo, Integer.parseInt(levelText.getText()), speedText.getText());
        }
    }

    private void updateRanks() {
        players.sort(PlayerInfo::compareTo);
        for(int i=0;i<players.size();i++){
            players.get(i).setRank(i+1);
        }
    }

    private void saveAndUpdateThePlayer() {
        updateRanks();
        PlayerSaver playerSaver=new BinaryPlayerSaver();
        playerSaver.saveAllPlayers(this.players);
        FirstMenu firstMenu=new FirstMenu(stage,scene,root);
    }

    public void virusReduction() {
        virusNumber++;
        scoreText.setText(String.valueOf(virusNumber));
    }

    public String[][] getMap() {
        return map;
    }

    public ArrayList<ArrayList<GameObject>> getGameObjects() {
        return gameObjects;
    }

    public Capsule getCurrentCapsule() {
        return currentCapsule;
    }

    public void setVirusNumber(int virusNumber) {
        this.virusNumber = virusNumber;
        virusText.setText(String.valueOf(virusNumber));
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
        topText.setText(String.valueOf(topScore));
    }

    public void setScore(int score) {
        this.score = score;
        if(score>playerInfo.getHighScore()){
            playerInfo.setHighScore(score);
            setTopScore(score);
        }
        scoreText.setText(String.valueOf(score));
    }
}
