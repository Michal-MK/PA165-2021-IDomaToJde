package cz.idomatojde.rest;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * REST API Token generator
 *
 * @author Dmitriy Dumanskiy (https://stackoverflow.com/questions/13992972/how-to-create-a-authentication-token-using-java)
 * adapted by Michal Hazdra
 */
public class TokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static String generateNewToken() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
