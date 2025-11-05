package userService.teachers.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.students.modals.enums.TOPIC_STATUS;
import userService.teachers.modal.TeacherAndTopics;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherAndTopicsRepository extends JpaRepository<TeacherAndTopics,Long> {
    List<TeacherAndTopics> findByTopicStatus(TOPIC_STATUS status);
}
