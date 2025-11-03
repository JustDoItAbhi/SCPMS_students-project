package userService.teachers.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.teachers.modal.Teachers;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teachers,Long> {
    // Exact match
    List<Teachers> findBySubject(String subject);

    // Fuzzy search with trimming and case-insensitive
    @Query("SELECT t FROM Teachers t WHERE LOWER(TRIM(t.subject)) = LOWER(TRIM(:subject))")
    List<Teachers> findBySubjectIgnoreCaseAndTrim(@Param("subject") String subject);

    @Query("SELECT t FROM Teachers t WHERE LOWER(TRIM(t.subject)) LIKE LOWER(CONCAT('%', TRIM(:subject), '%'))")
    List<Teachers> findBySubjectContainingIgnoreCaseAndTrim(@Param("subject") String subject);
}
