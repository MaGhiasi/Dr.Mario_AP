package ir.ac.kntu.saving;

import java.util.ArrayList;

public interface PlayerSaver {

    ArrayList<PlayerInfo> getAllPlayers();

    void saveAllPlayers(ArrayList<PlayerInfo> list);
}
