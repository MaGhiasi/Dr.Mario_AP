package ir.ac.kntu.constants;

public class Keeper {

    private static Keeper instance=new Keeper();

    public final double fastCoefficient= 1.5;

    public final double normalCoefficient= 1.1;

    public final int boardWidth= 8;

    public final int boardHeight= 16;

    public final int loading= 5;

    public final int virusAdditionLevel= 4;

    public int objectNumber = 1;

    private Keeper() {
    }

    private void addNUMBER() {
        this.objectNumber = objectNumber+1;
    }

    public static Keeper getInstance() {
        return instance;
    }

    public int getObjectNumber() {
        int i=objectNumber;
        addNUMBER();
        return i;
    }

}
