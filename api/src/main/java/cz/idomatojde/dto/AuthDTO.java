package cz.idomatojde.dto;

/**
 * Simple token DTO to obtain when authenticating
 *
 * @author Michal Hazdra
 */
public class AuthDTO {

    public AuthDTO(boolean success) {
        this.success = success;
    }

    String token;

    boolean success;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccessful() {
        return success;
    }

    public static AuthDTO Invalid() {
        AuthDTO ret = new AuthDTO(false);
        ret.token = "";
        return ret;
    }
}
