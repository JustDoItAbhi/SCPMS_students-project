package userService.students.modals;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
import userService.registrations.entities.Users;

import java.util.List;

@Getter
@Setter
@Entity
public class Students extends BaseModels  {
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Users user;
    private String currentYear;
    private String groupNumber;
    private String subGroupNumber;
    private String monitorName;
    private String studentIdCardNumber;
    private String passportNumber;

}
