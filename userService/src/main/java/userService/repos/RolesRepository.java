package userService.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.entities.Roles;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles>findByRoleName(String role);
}
