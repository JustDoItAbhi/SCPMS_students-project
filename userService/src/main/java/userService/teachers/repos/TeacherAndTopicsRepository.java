package userService.teachers.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import userService.students.modals.enums.TOPIC_STATUS;
import userService.teachers.modal.TeacherAndTopics;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherAndTopicsRepository extends JpaRepository<TeacherAndTopics,Long> {
    List<TeacherAndTopics> findByTopicStatus(TOPIC_STATUS status);
    Optional<TeacherAndTopics>findByTopicId(long topicId);
    List<TeacherAndTopics>findByTeacherId(long teacherId);
    @Modifying
    @Query("DELETE FROM TeacherAndTopics t WHERE t.teacherId = :teacherId")
    void deleteAllByTeacherId(@Param("teacherId") long teacherId);
}
