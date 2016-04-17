package saleksovski.scrum.service.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stefan on 2/20/16.
 */
public class StringUtils {

    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    private static Map<String, String> transposeMap;

    public static String transpose(String toTranspose) {
        StringBuilder transposed = new StringBuilder();
        for (String s:
             toTranspose.split("")) {

            String t = transposeMap.get(s);
            if (t != null) {
                transposed.append(t);
            } else {
                transposed.append(s);
            }
        }

        return transposed.toString();
    }

    static {
        transposeMap = new HashMap<>();
        transposeMap.put("a", "а");
        transposeMap.put("b", "б");
        transposeMap.put("c", "ц");
        transposeMap.put("d", "д");
        transposeMap.put("e", "е");
        transposeMap.put("f", "ф");
        transposeMap.put("g", "г");
        transposeMap.put("h", "х");
        transposeMap.put("i", "и");
        transposeMap.put("j", "ј");
        transposeMap.put("k", "к");
        transposeMap.put("l", "л");
        transposeMap.put("m", "м");
        transposeMap.put("n", "н");
        transposeMap.put("o", "о");
        transposeMap.put("p", "п");
        transposeMap.put("q", "љ");
        transposeMap.put("r", "р");
        transposeMap.put("s", "с");
        transposeMap.put("t", "т");
        transposeMap.put("u", "у");
        transposeMap.put("v", "в");
        transposeMap.put("w", "в");
        transposeMap.put("x", "џ");
        transposeMap.put("y", "ѕ");
        transposeMap.put("z", "з");

        transposeMap.put("а", "a");
        transposeMap.put("б", "b");
        transposeMap.put("в", "v");
        transposeMap.put("г", "g");
        transposeMap.put("д", "d");
        transposeMap.put("ѓ", "gj");
        transposeMap.put("е", "e");
        transposeMap.put("ж", "zh");
        transposeMap.put("з", "z");
        transposeMap.put("ѕ", "dz");
        transposeMap.put("и", "i");
        transposeMap.put("ј", "j");
        transposeMap.put("к", "k");
        transposeMap.put("л", "l");
        transposeMap.put("љ", "lj");
        transposeMap.put("м", "m");
        transposeMap.put("н", "n");
        transposeMap.put("њ", "nj");
        transposeMap.put("о", "o");
        transposeMap.put("п", "p");
        transposeMap.put("р", "r");
        transposeMap.put("с", "s");
        transposeMap.put("т", "t");
        transposeMap.put("ќ", "kj");
        transposeMap.put("у", "u");
        transposeMap.put("ф", "f");
        transposeMap.put("х", "h");
        transposeMap.put("ц", "c");
        transposeMap.put("ч", "ch");
        transposeMap.put("џ", "dz");
        transposeMap.put("ш", "sh");
    }

}
