package ir.ac.kntu.map;

import ir.ac.kntu.constants.Keeper;
import ir.ac.kntu.objects.*;
import ir.ac.kntu.objects.drug.pill.Normal;
import ir.ac.kntu.objects.myVirus.BlueVirus;
import ir.ac.kntu.objects.myVirus.RedVirus;
import ir.ac.kntu.objects.myVirus.YellowVirus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapParser {
    private Map<String,GameObjectCreator> mapTranslator;

    public MapParser(){
        mapTranslator=initializeTranslator();
    }

    private Map<String, GameObjectCreator> initializeTranslator() {
        //System.out.println(players);
        Map<String,GameObjectCreator> mapTranslator=new HashMap<>();
        mapTranslator.put("n",(row,column)->new Normal(Keeper.getInstance().getObjectNumber(),row,column));
        mapTranslator.put("r",(row,column)->new RedVirus(Keeper.getInstance().getObjectNumber(),row,column));
        mapTranslator.put("b",(row,column)->new BlueVirus(Keeper.getInstance().getObjectNumber(),row,column));
        mapTranslator.put("y",(row,column)->new YellowVirus(Keeper.getInstance().getObjectNumber(),row,column));
        return mapTranslator;
    }

    public ArrayList<ArrayList<GameObject>> gameObjects(String[][] map){
        ArrayList<ArrayList<GameObject>> result = new ArrayList<>();
        for (int i= 0; i < map.length; i++) {
            ArrayList<GameObject> result1 = new ArrayList<>();
            for (int j = 0; j < map[i].length; j++) {
                //result1.add(new Normal(Keeper.getInstance().getOBJECT_NUMBER(), i,j));
                result1.add(mapTranslator.get(map[i][j]).create(i,j));
            }
            result.add(result1);
        }
        return result;
    }
}
