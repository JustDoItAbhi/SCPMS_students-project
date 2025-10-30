package userService.students.modals;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.Users;
@Getter
@Setter
@Entity
public class Students extends Users {
    private String currentYear;
    private String groupNumber;
    private String subGroupNumber;
    private String monitorName;
}
