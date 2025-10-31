package userService.students.modals;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.BaseModels;
import userService.registrations.entities.Users;

import java.util.List;

@Getter
@Setter
@Entity
public class Students extends BaseModels  {
    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id",unique = true)
    private Users user;
    private String currentYear;
    private String groupNumber;
    private String subGroupNumber;
    private String monitorName;
    private String studentIdCardNumber;
    private String passportNumber;

}
