package userService.security.registeredClinetController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import userService.security.CustomizeRegeredClientService;
import userService.security.dtos.ClientRequestDto;

@RestController
@RequestMapping("/client")
public class ClientController {// registrerd OICD client controller
    @Autowired
    public CustomizeRegeredClientService service;// constructor
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")// post mapping to register a client only ADMIN can use
    public ResponseEntity<RegisteredClient> createClient(@RequestBody ClientRequestDto dto){
        return ResponseEntity.ok(service.createRegeretedClient(dto));
    }
}

// expected response
//{
//        "id": "27510c39-1af8-4869-9022-213d7503573c",
//        "clientId": "sahib",
//        "clientIdIssuedAt": "2025-10-18T13:45:57.348232900Z",
//        "clientSecret": "$2a$10$GJOhasJf7Raou3HLPd.FUeJCAvgbydrrvmgrOROj5fiq8XinzQ2P.",
//        "clientSecretExpiresAt": null,
//        "clientName": "sahib",
//        "clientAuthenticationMethods": [
//        {
//        "value": "client_secret_basic"
//        }
//        ],
//        "authorizationGrantTypes": [
//        {
//        "value": "refresh_token"
//        },
//        {
//        "value": "client_credentials"
//        },
//        {
//        "value": "urn:ietf:params:oauth:grant-type:device_code"
//        },
//        {
//        "value": "authorization_code"
//        },
//        {
//        "value": "urn:ietf:params:oauth:grant-type:token-exchange"
//        },
//        {
//        "value": "urn:ietf:params:oauth:grant-type:jwt-bearer"
//        }
//        ],
//        "redirectUris": [
//        "https://oauth.pstmn.io/v1/callback"
//        ],
//        "postLogoutRedirectUris": [
//        "http://127.0.0.1:8090/"
//        ],
//        "scopes": [
//        "openid",
//        "profile"
//        ],
//        "clientSettings": {
//        "settings": {
//        "settings.client.require-proof-key": false,
//        "settings.client.require-authorization-consent": true
//        },
//        "jwkSetUrl": null,
//        "requireAuthorizationConsent": true,
//        "requireProofKey": false,
//        "tokenEndpointAuthenticationSigningAlgorithm": null,
//        "x509CertificateSubjectDN": null
//        },
//        "tokenSettings": {
//        "settings": {
//        "settings.token.reuse-refresh-tokens": true,
//        "settings.token.x509-certificate-bound-access-tokens": false,
//        "settings.token.id-token-signature-algorithm": "RS256",
//        "settings.token.access-token-time-to-live": "PT5M",
//        "settings.token.access-token-format": {
//        "value": "self-contained"
//        },
//        "settings.token.refresh-token-time-to-live": "PT1H",
//        "settings.token.authorization-code-time-to-live": "PT5M",
//        "settings.token.device-code-time-to-live": "PT5M"
//        },
//        "refreshTokenTimeToLive": "PT1H",
//        "reuseRefreshTokens": true,
//        "accessTokenFormat": {
//        "value": "self-contained"
//        },
//        "accessTokenTimeToLive": "PT5M",
//        "idTokenSignatureAlgorithm": "RS256",
//        "authorizationCodeTimeToLive": "PT5M",
//        "x509CertificateBoundAccessTokens": false,
//        "deviceCodeTimeToLive": "PT5M"
//        }
//        }