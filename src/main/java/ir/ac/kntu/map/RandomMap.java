package ir.ac.kntu.map;

import ir.ac.kntu.util.RandomHelper;

public class RandomMap {
    /*private String[] address={"src/main/resources/mapObjects/blueVirus.png",
            "src/main/resources/mapObjects/redVirus.png",
            "src/main/resources/mapObjects/yellowVirus.png",
            "src/main/resources/mapObjects/normal.png"};*/
    private String[] address = {"b", "r", "y", "n"};

    public RandomMap() {
    }

    public String[][] makeMap(int virusNumber, int row, int column) {
        String[][] outPut = new String[row][column];
        int r = 0, b = 0, y = 0, whole = row * column, virus = 0,k=0,normalNum = whole - virusNumber;
        int upNormal = ((normalNum / 2) / column) * column;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                outPut[i][j] = address[3];
            }
        }
        while (virus < virusNumber) {
            int i = (upNormal / column) + RandomHelper.randomInt(row - (upNormal / column));
            int j = RandomHelper.randomInt(column),e = RandomHelper.randomInt(3);
            outPut=makeSureHaveAllType(outPut,upNormal,column,row);
            b++;r++;y++;virus+=3;
            if(outPut[i][j]==address[3]) {
                outPut[i][j] = address[e];
                if (e == 0) {
                    b++;
                } else if (e == 1) {
                    r++;
                } else if (e == 2) {
                    y++;
                }
                virus++;
            }
        }
        return outPut;
    }

    public String[][] makeSureHaveAllType(String[][] outPut,int upNormal,int column,int row) {
        int r=0,y=0;
        int u = (upNormal / column) + RandomHelper.randomInt(row - (upNormal / column));
        int v = RandomHelper.randomInt(column);
        outPut[u][v] = address[0];
        while (r ==0) {
            int i = (upNormal / column) + RandomHelper.randomInt(row - (upNormal / column));
            int j = RandomHelper.randomInt(column);
            if(outPut[i][j]==address[3]) {
                outPut[i][j] = address[1];
                r++;
            }
        }
        while (y ==0) {
            int i = (upNormal / column) + RandomHelper.randomInt(row - (upNormal / column));
            int j = RandomHelper.randomInt(column);
            if(outPut[i][j]==address[3]) {
                outPut[i][j] = address[2];
                y++;
            }
        }
        return outPut;
    }
}


