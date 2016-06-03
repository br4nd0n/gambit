package mab.tests;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by brobrien on 4/27/16.
 */
public class UUIDTest {

    public static void main(String[] args) {
        int keysetSize = 1000000; //test 10M all unique. 10M took longer than 10x 1M
        Set<String> keys = new HashSet<String>(keysetSize);

        for (int i =0; i<keysetSize;++i)
            keys.add(UUID.randomUUID().toString());

        int uniqueKeys = keys.size();

        if (keysetSize == uniqueKeys)
            System.out.println("All keys are unique");
        else
            System.out.println("Uhh....");
        //System.out.println("keys: "+keysetSize+", unique keys: "+uniqueKeys);
    }
}
