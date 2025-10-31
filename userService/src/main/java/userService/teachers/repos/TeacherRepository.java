package userService.teachers.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.teachers.modal.Teachers;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teachers,Long> {
//    Optional<Teachers>findBySubject(String subject);
    List<Teachers>findBySubject(String subject);
}
