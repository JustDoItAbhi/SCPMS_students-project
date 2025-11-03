package userService.students.modals;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
@Getter
@Setter
@Entity
public class StudentTopic extends BaseModels {
    private long teacherId;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "studentand_subject_id")
    private StudentAndSubject studentAndSubject;
    private String topic;
}
