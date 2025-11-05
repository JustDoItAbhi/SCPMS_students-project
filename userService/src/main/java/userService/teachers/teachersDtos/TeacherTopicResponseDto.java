package userService.teachers.teachersDtos;

import lombok.Getter;
import lombok.Setter;
import userService.students.modals.enums.TOPIC_STATUS;

@Getter
@Setter
public class TeacherTopicResponseDto {
        private long teacherTopicId;
        private TOPIC_STATUS topicStatus;
        private long topicId;
        private long studentSubjectId;
        private long teacherId;
        private String topic;
    }

