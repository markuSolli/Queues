package no.ntnu.fullstack.queues.authentication.jwt;

/**
 * Wrapper for a response with access token. Used on successful login
 */
public class JwtResponse {
    private String accessToken;

    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
