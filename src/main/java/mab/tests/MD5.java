package mab.tests;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by brobrien on 4/15/16.
 */
public class MD5 {



    public static void main(String[] args) {
        String shop1 = "shop1";
        String shop2 = "shop2";

        System.out.println(getHash(shop1));
        System.out.println(getHash(shop2));

        System.out.println(getCompoundHash(shop1, shop2));
        System.out.println(getCompoundHash(shop2, shop1));

    }

    private static String getCompoundHash(String s1, String s2) {

        String hash1 = getHash(s1);
        String hash2 = getHash(s2);

        String[] elements = {hash1,hash2};

        Arrays.sort(elements);

        String compoundString = elements[0]+elements[1];

        return getHash(compoundString);
    }

    private static void testHash() {
        try {

            String date1="2016-05-01";
            String date2="2017-05-04T12:12:45";

            System.out.println(date1.replace("-","").substring(0,8));
            System.out.println(date2.replace("-","").substring(0,8));

            MessageDigest md = MessageDigest.getInstance("SHA-1"); //MD5, SHA-1

            String dataSet1 = "best data ever";
            String dataSet2 = "best data ever";

            byte[] digest1 = md.digest(dataSet1.getBytes());
            byte[] digest2 = md.digest(dataSet2.getBytes());

            System.out.println(digest1.length+", "+DigestUtils.sha1Hex(dataSet1));
            System.out.println(digest2.length+", "+DigestUtils.sha1Hex(dataSet2));
            System.out.println(MessageDigest.isEqual(digest1,digest2));



            //System.out.println(DigestUtils.sha1Hex(dataSet1));
            //System.out.println(DigestUtils.sha1Hex(dataSet2));
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getHash(String s) {
        return DigestUtils.sha1Hex(s);
    }
}
