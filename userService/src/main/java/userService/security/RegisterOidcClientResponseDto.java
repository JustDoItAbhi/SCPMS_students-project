package userService.security;

import lombok.Data;

@Data
public class RegisterOidcClientResponseDto {
    private String clientId;
    private String clientSecret;
    private String id;
    private String message;

    public RegisterOidcClientResponseDto(String clientId, String clientSecret, String id, String message) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.id = id;
        this.message = message;
    }
}