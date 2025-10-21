package userService.security.dtos;

public class ClientRequestDto {// oicd client request dto
    private String clientId;// client id
    private String clientSecret;// client password (encoded)
    private String clientName;// client name
    private String redirectUris;// redirect url
    private String postLogoutRedirectUris;// post logout redirect url
    //    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;
// getters and setters
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getPostLogoutRedirectUris() {
        return postLogoutRedirectUris;
    }

    public void setPostLogoutRedirectUris(String postLogoutRedirectUris) {
        this.postLogoutRedirectUris = postLogoutRedirectUris;
    }
}
//{
//        "clientId":"sahib",
//        "clientSecret":"sahib123",
//        "clientName":"sahib",
//        "redirectUris":"https://oauth.pstmn.io/v1/callback",
//        "postLogoutRedirectUris":"http://127.0.0.1:8090/"
//        }