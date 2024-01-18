package ir.ac.kntu.saving;

import java.io.*;
import java.util.ArrayList;

public class BinaryPlayerSaver implements PlayerSaver{

    @Override
    public ArrayList<PlayerInfo> getAllPlayers(){
        File file=new File("src/main/resources/Players.txt");
        ArrayList<PlayerInfo> players=new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
            while(true) {
                try {
                    players.add((PlayerInfo) input.readObject());
                } catch(EOFException e){
                    break;
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("#  No Such File found  #");
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("#  Nothing exists in file #");
        }
        return players;
    }

    @Override
    public void saveAllPlayers(ArrayList<PlayerInfo> list) {
        ArrayList<PlayerInfo> players=getAllPlayers();
        System.out.println(players);
        System.out.println(list);
        for (int i=0;i<players.size();i++){
            for(int j=0;j< list.size();j++){
                if(list.get(j).equals(players.get(i))){
                    players.remove(i);
                    players.add(list.get(j));
                }else{
                    if(!players.contains(list.get(j))){
                        players.add(list.get(j));
                    }
                }
            }
        }
        if(players.size()==0){
            players.addAll(list);
        }
        System.out.println(players);
        File file=new File("src/main/resources/Players.txt");
        try(FileOutputStream fileOutputStream=new FileOutputStream(file,false);
            ObjectOutputStream output=new ObjectOutputStream(fileOutputStream)){
            for(PlayerInfo player:players){
                output.writeObject(player);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
