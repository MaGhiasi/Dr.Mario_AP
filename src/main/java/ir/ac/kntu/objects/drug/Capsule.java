package ir.ac.kntu.objects.drug;

import ir.ac.kntu.constants.Keeper;
import ir.ac.kntu.objects.Direction;
import ir.ac.kntu.objects.GameObject;
import ir.ac.kntu.objects.drug.pill.BluePill;
import ir.ac.kntu.objects.drug.pill.Normal;
import ir.ac.kntu.objects.drug.pill.RedPill;
import ir.ac.kntu.objects.drug.pill.YellowPill;

import java.util.ArrayList;
import java.util.List;

import static ir.ac.kntu.objects.Direction.DOWN;

public class Capsule {
    private Direction direction= DOWN;

    private int type;//red blue yellow redblue yellowblue redyellow

    private int rotation;

    private ArrayList<GameObject> parts = new ArrayList<>();

    public Capsule(int type) {
        this.type = type;
        this.rotation = 0;
        createParts();
    }

    private void createParts() {
        if (type==0) {
            parts.add(new RedPill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new RedPill(Keeper.getInstance().getObjectNumber(), 0,4));
        }

        if (type==1) {
            parts.add(new BluePill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new BluePill(Keeper.getInstance().getObjectNumber(), 0,4));
        }

        if (type==2) {
            parts.add(new YellowPill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new YellowPill(Keeper.getInstance().getObjectNumber(), 0,4));
        }

        if (type==3) {
            parts.add(new RedPill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new BluePill(Keeper.getInstance().getObjectNumber(), 0,4));
        }

        if (type==4) {
            parts.add(new RedPill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new YellowPill(Keeper.getInstance().getObjectNumber(), 0,4));
        }
        if (type==5) {
            parts.add(new YellowPill(Keeper.getInstance().getObjectNumber(), 0,3));
            parts.add(new BluePill(Keeper.getInstance().getObjectNumber(), 0,4));
        }
    }

    public GameObject getPartOne() {
        return parts.get(0);
    }

    public GameObject getPartTwo() {
        return parts.get(1);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public List<GameObject> getParts() {
        return parts;
    }

    public void setParts(ArrayList<GameObject> parts) {
        this.parts = parts;
    }

    public boolean checkCanMoveDown(String[][] map) {
        if(rotation==0||rotation==2) {
            return ((parts.get(0).getRowIndex())<15&&(parts.get(1).getRowIndex())<15)&&
                    (map[parts.get(0).getRowIndex()+1][parts.get(0).getColumnIndex()].equals("n")&&
                    map[parts.get(1).getRowIndex()+1][parts.get(1).getColumnIndex()].equals("n"));
        }else {
            return((parts.get(0).getRowIndex())<15&&(parts.get(1).getRowIndex())<15)&&
                    (map[parts.get(0).getRowIndex()+1][parts.get(0).getColumnIndex()].equals("n")||
             map[parts.get(1).getRowIndex()+1][parts.get(1).getColumnIndex()].equals("n"));
        }
    }

    public boolean checkCanMoveRight(String[][] map) {
        if(rotation==0||rotation==2) {
            return ((parts.get(0).getColumnIndex())<7 &&(parts.get(1).getColumnIndex())<7)&&
                    (map[parts.get(0).getRowIndex()][parts.get(0).getColumnIndex()+1].equals("n")||
                    map[parts.get(1).getRowIndex()][parts.get(1).getColumnIndex()+1].equals("n"));
        }else {
            return map[parts.get(0).getRowIndex()][parts.get(0).getColumnIndex()+1].equals("n")&&
                map[parts.get(1).getRowIndex()][parts.get(1).getColumnIndex()+1].equals("n");
        }
    }

    public boolean checkCanMoveLeft(String[][] map) {
        if(rotation==0||rotation==2) {
            return ((parts.get(0).getColumnIndex()-1)>0 &&(parts.get(1).getColumnIndex()-1)>0)&&
                    (map[parts.get(0).getRowIndex()][parts.get(0).getColumnIndex()-1].equals("n")||
                    map[parts.get(1).getRowIndex()][parts.get(1).getColumnIndex()-1].equals("n"));
        }else {
            return map[parts.get(0).getRowIndex()][parts.get(0).getColumnIndex()-1].equals("n")&&
             map[parts.get(1).getRowIndex()][parts.get(1).getColumnIndex()-1].equals("n");
        }
    }

    public boolean checkCanRotate(String[][] map) {
        if(rotation==0||rotation==2) {
            if(getPartOne().getColumnIndex()>=Keeper.getInstance().boardWidth||
                    getPartOne().getRowIndex()>= Keeper.getInstance().boardHeight||
                    getPartOne().getRowIndex()<=0 ||getPartOne().getColumnIndex()<=0||
                    !map[getPartTwo().getRowIndex()-1][getPartTwo().getColumnIndex()].equals("n")||
                    !map[getPartOne().getRowIndex()-1][getPartOne().getColumnIndex()].equals("n")) {
                return false;
            }else {
                return true;
            }
        }else {
            if(getPartOne().getColumnIndex()>=Keeper.getInstance().boardWidth||
                    getPartOne().getRowIndex()>= Keeper.getInstance().boardHeight||
                    getPartOne().getRowIndex()<=0 ||getPartOne().getColumnIndex()<=0||
                    !(map[getPartTwo().getRowIndex()][getPartTwo().getColumnIndex()-1].equals("n"))||
                    !(map[getPartOne().getRowIndex()][getPartOne().getColumnIndex()-1].equals("n"))) {
                return false;
            }else{
                return true;
            }
        }
    }

    public void notify(ArrayList<ArrayList<GameObject>> gameObjects,String [][] map) {
        switch (direction){
            case DOWN:
                if(checkCanMoveDown(map)){
                    moveDown(map,gameObjects);
                }
                break;
            case RIGHT:
                if(checkCanMoveRight(map)){
                    moveRight(map,gameObjects);
                }
                this.direction= Direction.RIGHT;
                break;
            case LEFT:
                if(checkCanMoveLeft(map)){
                    moveLeft(map,gameObjects);
                }
                this.direction= Direction.LEFT;
                break;
            case SHIFT:
                if(checkCanRotate(map)) {
                    rotate(map, gameObjects);
                }
            default:
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        System.out.println("setting direction");
        this.direction = direction;
    }

    public void moveDown(String[][] map,ArrayList<ArrayList<GameObject>> gameObjects) {
        if(rotation==0||rotation==2) {
            for (GameObject i : parts) {
                gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
                gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),
                        new Normal(Keeper.getInstance().getObjectNumber(), i.getRowIndex(), i.getColumnIndex()));
                map[i.getRowIndex()][i.getColumnIndex()] = ("n");
                i.moveY(1);
                gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
                gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(), i);
                map[i.getRowIndex()][i.getColumnIndex()] = i.getType();
            }
        }else{
            moveDowHorizontal(map, gameObjects);
        }
    }

    private void moveDowHorizontal(String[][] map, ArrayList<ArrayList<GameObject>> gameObjects) {
        int i1=getPartOne().getRowIndex(),i2=getPartTwo().getRowIndex(),i3;
        if(i2>i1){
            i3=i2;
        }else {
            i3=i1;
            System.out.println("   i3 ");
            System.out.println(i3);
        }
        int j=getPartOne().getColumnIndex();
        gameObjects.get(i3-1).remove(j);
        gameObjects.get(i3).remove(j);
        gameObjects.get(i3+1).remove(j);
        gameObjects.get(i3-1).add(j,
                new Normal(Keeper.getInstance().getObjectNumber(), i3-1, j));
        map[i3-1][j] = ("n");
        getPartOne().moveY(1);
        getPartTwo().moveY(1);
        gameObjects.get(getPartOne().getRowIndex()).add(j, getPartOne());
        map[getPartOne().getRowIndex()][j] = getPartOne().getType();
        gameObjects.get(getPartTwo().getRowIndex()).add(j, getPartTwo());
        map[getPartTwo().getRowIndex()][j] = getPartTwo().getType();
    }


    public void moveLeft(String[][] map,ArrayList<ArrayList<GameObject>> gameObjects) {
        for (GameObject i : parts) {
            gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
            gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),
                    new Normal(Keeper.getInstance().getObjectNumber(),i.getRowIndex(),i.getColumnIndex()));
            map[i.getRowIndex()][i.getColumnIndex()]=("n");
            i.moveX(-1);
            gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
            gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),i);
            map[i.getRowIndex()][i.getColumnIndex()]=i.getType();
        }
    }


    public void moveRight(String[][] map,ArrayList<ArrayList<GameObject>> gameObjects) {
        GameObject i = getPartTwo();
        gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
        gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),
                new Normal(Keeper.getInstance().getObjectNumber(),i.getRowIndex(),i.getColumnIndex()));
        map[i.getRowIndex()][i.getColumnIndex()]=("n");
        i.moveX(1);
        gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
        gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),i);
        map[i.getRowIndex()][i.getColumnIndex()]=i.getType();
        i = getPartOne();
        gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
        gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),
                new Normal(Keeper.getInstance().getObjectNumber(),i.getRowIndex(),i.getColumnIndex()));
        map[i.getRowIndex()][i.getColumnIndex()]=("n");
        i.moveX(1);
        gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
        gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),i);
        map[i.getRowIndex()][i.getColumnIndex()]=i.getType();
    }

    public int helper() {
        int lowX =50;
        for (GameObject i : parts) {
            if (i.getColumnIndex() < lowX) {
                lowX = i.getColumnIndex();
            }
        }
        return lowX;
    }

    public int helper2() {
        int lowY =50;
        for (GameObject i : parts) {
            if (i.getRowIndex() < lowY) {
                lowY = i.getRowIndex();
            }
        }
        return lowY;
    }

    public void rotate(String[][] map,ArrayList<ArrayList<GameObject>> gameObjects) {
        int lowX = helper();
        int lowY = helper2();
        helper3();
        for (GameObject i : parts) {
            gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
            gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),
                    new Normal(Keeper.getInstance().getObjectNumber(),i.getRowIndex(),i.getColumnIndex()));
            map[i.getRowIndex()][i.getColumnIndex()]=("n");
            if (rotation == 0) {
                i.setRowAndColumn(i.getRowIndex() - lowY + lowX + 1,
                        i.getColumnIndex() - lowX + lowY - 1);
            } else if (rotation == 1) {
                i.setRowAndColumn(i.getRowIndex() - lowY + lowX - 1,
                        i.getColumnIndex() - lowX + lowY + 1);
            } else if (rotation == 2) {
                i.setRowAndColumn(i.getRowIndex() - lowY + lowX + 1,
                        i.getColumnIndex() - lowX + lowY - 1);
            } else {
                i.setRowAndColumn(i.getRowIndex() - lowY + lowX - 1,
                        i.getColumnIndex() - lowX + lowY + 1);
            }
            gameObjects.get(i.getRowIndex()).remove(i.getColumnIndex());
            gameObjects.get(i.getRowIndex()).add(i.getColumnIndex(),i);
            map[i.getRowIndex()][i.getColumnIndex()]=i.getType();
        }
    }

    private void helper3() {
        if(rotation==1||rotation==3){
            String theType=parts.get(0).getType();
            parts.get(0).setType(parts.get(1).getType());
            parts.get(0).setType(theType);
        }
        rotation = (rotation + 1) % 4;
    }

}
