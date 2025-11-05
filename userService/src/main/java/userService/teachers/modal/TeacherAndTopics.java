package userService.teachers.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
import userService.students.modals.enums.TOPIC_STATUS;

@Getter
@Setter
@Entity
public class TeacherAndTopics extends BaseModels {
    @Enumerated(EnumType.STRING)
    private TOPIC_STATUS topicStatus;
    private long topicId;
    private long studentSubjectId;
    private long teacherId;
    private String topic;

}
