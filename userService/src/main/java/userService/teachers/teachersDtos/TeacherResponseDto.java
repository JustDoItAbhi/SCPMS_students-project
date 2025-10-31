package userService.teachers.teachersDtos;

import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.Users;

@Getter
@Setter
public class TeacherResponseDto {
    private String subject;
    private Users users;
}
