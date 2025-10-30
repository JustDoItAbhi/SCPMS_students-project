package userService.registrations.security.authRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.registrations.security.authEntity.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {// client repository
    Optional<Client> findByClientId(String clientId);
}