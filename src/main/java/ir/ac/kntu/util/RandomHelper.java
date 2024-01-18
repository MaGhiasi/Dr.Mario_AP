package ir.ac.kntu.util;

import java.security.SecureRandom;

public class RandomHelper {
    private static final SecureRandom GENERATOR = new SecureRandom();

    public static int randomInt(int bound){
        return GENERATOR.nextInt(bound);
    }

    public static double randomDouble(){
        return GENERATOR.nextDouble();
    }

    public static boolean randomBoolean(){
        return GENERATOR.nextBoolean();
    }

}
