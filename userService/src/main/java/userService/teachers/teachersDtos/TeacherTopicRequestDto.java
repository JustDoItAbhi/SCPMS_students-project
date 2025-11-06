package userService.teachers.teachersDtos;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import userService.students.modals.enums.TOPIC_STATUS;
@Getter
@Setter
public class TeacherTopicRequestDto {
//    private long teacherTopicId;
    private String topicStatus;
    private long topicId;
    private long studentSubjectId;
    private long teacherId;
    private String topic;
}
