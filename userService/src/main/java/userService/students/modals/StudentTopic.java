package userService.students.modals;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
@Getter
@Setter
@Entity
public class StudentTopic extends BaseModels {
    private long teacherId;
    private long studentandSubjectId;
    private String topic;
}
