package userService.registrations.security.authRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.registrations.security.authEntity.Authorization;

import java.util.Optional;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, String> {// authorization repository
    Optional<Authorization> findByState(String state); // find authorization with its state
    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);// find authorization with its code value
    Optional<Authorization> findByAccessTokenValue(String accessToken);//find authorization with its token valu
    Optional<Authorization> findByRefreshTokenValue(String refreshToken);// find authorization with its referesh token value
    Optional<Authorization> findByOidcIdTokenValue(String idToken);// find authorization with its ocid token value
    Optional<Authorization> findByUserCodeValue(String userCode);//find authorization with its user code value
    Optional<Authorization> findByDeviceCodeValue(String deviceCode);//find authorization with its device code value
    @Query("select a from Authorization a where a.state = :token" +// default query from spring boot authorization server
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token" +
            " or a.userCodeValue = :token" +
            " or a.deviceCodeValue = :token"
    )
    Optional<Authorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}