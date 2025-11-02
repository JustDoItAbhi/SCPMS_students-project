package userService.students.studentRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.students.modals.StudentTopic;

import java.util.Optional;

@Repository
public interface StudentTopicRepo extends JpaRepository<StudentTopic,Long> {

}
