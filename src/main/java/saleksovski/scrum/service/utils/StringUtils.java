package saleksovski.scrum.service.utils;

import java.security.SecureRandom;

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

}
