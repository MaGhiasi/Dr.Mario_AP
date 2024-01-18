package ir.ac.kntu.keyboard;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.objects.Direction;
import ir.ac.kntu.objects.drug.Capsule;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class KeyLogger implements EventHandler<KeyEvent> {

    private GameLoop game;

    public KeyLogger(Scene scene,GameLoop gameLoop){
        this.game=gameLoop;
        scene.setOnKeyPressed(this::handle);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        Direction direction=keyEventToDirection(keyEvent);
        Capsule current=game.getCurrentCapsule();
        if(current!=null){
            if(direction!=null) {
                current.setDirection(direction);
                current.notify(game.getGameObjects(),game.getMap());
            }
        }
    }

    public Direction keyEventToDirection(KeyEvent keyEvent){
        switch (keyEvent.getCode()){
            case DOWN:
                System.out.println("direction is down");
                return Direction.DOWN;
            case RIGHT:
                System.out.println("direction is right");
                return Direction.RIGHT;
            case LEFT:
                System.out.println("direction is left");
                return Direction.LEFT;
            case SHIFT:
                return Direction.SHIFT;
            case UP:
                return Direction.UP;
            default:
                System.out.println("direction is null");
                return null;
        }
    }

}
