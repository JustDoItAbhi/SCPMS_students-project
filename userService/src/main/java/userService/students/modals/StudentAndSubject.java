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
public class StudentAndSubject extends BaseModels {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)

    private Students students;
    private String Subject;
    private String subjectYear;
}
