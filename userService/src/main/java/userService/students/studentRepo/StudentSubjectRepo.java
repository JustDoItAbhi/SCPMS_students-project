package userService.students.studentRepo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.students.modals.StudentAndSubject;

import java.util.Optional;

@Repository
public interface StudentSubjectRepo extends JpaRepository<StudentAndSubject,Long> {
    @Query("SELECT sas FROM StudentAndSubject sas WHERE sas.students.user.id = :userId")
    Optional<StudentAndSubject> findByUserId(@Param("userId") long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM StudentAndSubject sas WHERE sas.students.user.id = :userId")
    void deleteByUserId(@Param("userId") long userId);
}
