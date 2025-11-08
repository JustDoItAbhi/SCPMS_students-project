package userService.registrations.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.registrations.entities.TeachrUser;

import java.util.Optional;

@Repository
public interface TeacherUserRepository extends JpaRepository<TeachrUser,Long> {
    Optional<TeachrUser>findByTeacherEmail(String teacherEmail);
    Optional<TeachrUser>findByRole(String teacherRole);
}
