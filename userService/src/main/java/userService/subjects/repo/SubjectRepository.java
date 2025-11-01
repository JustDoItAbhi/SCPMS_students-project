package userService.subjects.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.subjects.Subjects;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subjects,Long> {
Optional<Subjects> findBySubject(String subject);
Optional<Subjects> findByCourseYear(String year);
}
