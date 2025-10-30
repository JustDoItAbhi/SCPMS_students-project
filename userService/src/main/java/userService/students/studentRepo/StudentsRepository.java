package userService.students.studentRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.registrations.entities.Users;
@Repository
public interface StudentsRepository extends JpaRepository<Users,Long> {
}
