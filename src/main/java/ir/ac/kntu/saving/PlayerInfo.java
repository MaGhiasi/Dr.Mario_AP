package ir.ac.kntu.saving;

import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class PlayerInfo implements Serializable,Comparable<PlayerInfo>{
    private String name;

    private int numOfGame;

    private int highScore;

    private int rank;

    private boolean haveUnfinishedGame;

    private GameInfo gameInfo=null;

    public PlayerInfo( ){  }

    public PlayerInfo(String name){
        this.name=name;
        this.haveUnfinishedGame=false;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "name='" + name + '\'' +
                ", numOfGame=" + numOfGame +
                ", highScore=" + highScore +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this==o){
            return true;
        }
        if (o==null || getClass() !=o.getClass()){
            return false;
        }
        PlayerInfo that = (PlayerInfo) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public String getName() {
        return name;
    }

    public void plusNumOfGame(){
        numOfGame++;
    }

    public int getNumOfGame() {
        return numOfGame;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfGame(int numOfGame) {
        this.numOfGame = numOfGame;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isHaveUnfinishedGame() {
        return haveUnfinishedGame;
    }

    public void setHaveUnfinishedGame(boolean haveUnfinishedGame) {
        this.haveUnfinishedGame = haveUnfinishedGame;
    }

    @Override
    public int compareTo(@NotNull PlayerInfo o) {
        return highScore-o.highScore;
    }

}
