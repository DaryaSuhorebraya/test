package by.epam.movierating.service.util;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * Provides util methods of service layer
 */
public class ServiceUtil {
    /**
     * Encode the password with md5 algorithm
     * @param password a password that has to be encoded
     * @return encoded password
     */
    public static String encodePassword(byte[] password){
        return DigestUtils.md5Hex(password);
    }
}
