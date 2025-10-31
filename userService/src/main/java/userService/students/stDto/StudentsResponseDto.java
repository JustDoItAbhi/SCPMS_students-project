package userService.students.stDto;

import lombok.Getter;
import lombok.Setter;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.registrations.entities.Users;

@Getter
@Setter
public class StudentsResponseDto {
    private Users users;
    private String currentYear;
    private String groupNumber;
    private String subGroupNumber;
    private String monitorName;
    private String studentIdCardNumber;
    private String passportNumber;
}
