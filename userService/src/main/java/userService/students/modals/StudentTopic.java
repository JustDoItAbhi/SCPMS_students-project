package userService.students.modals;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
import userService.students.modals.enums.TOPIC_STATUS;


@Getter
@Setter
@Entity
public class StudentTopic extends BaseModels {
    private long teacherId;
    @OneToOne(optional = false) // This makes the relationship mandatory
    @JoinColumn(name = "studentand_subject_id", nullable = false)
    private StudentAndSubject studentAndSubject;
    private String topic;
    @Enumerated(EnumType.STRING)
    private TOPIC_STATUS teacherAprovels;
}
