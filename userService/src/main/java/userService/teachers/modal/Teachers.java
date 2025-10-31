package userService.teachers.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
import userService.registrations.entities.Users;

@Getter
@Setter
@Entity
public class Teachers extends BaseModels {
    @OneToOne
    private Users users;
    private String subject;
}
