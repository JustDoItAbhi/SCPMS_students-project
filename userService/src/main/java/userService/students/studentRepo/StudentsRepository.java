package userService.students.studentRepo;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.registrations.entities.Users;
import userService.students.modals.Students;

import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Students,Long> {
    Optional<Students> findByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Students s WHERE s.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
