package userService.subjects;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
@Getter
@Setter
@Entity
public class Subjects extends BaseModels {
    private String courseYear;
    private String subject;
}
