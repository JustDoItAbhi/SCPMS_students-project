package userService.teachers.teachersDtos;

import lombok.Getter;
import lombok.Setter;
import userService.registrations.entities.Users;

@Getter
@Setter
public class UpdateTeacherResponeDto {
    private long teacherId;
    private String teacherName;
    private String subject;
    private Users users;
    private String teacherEmail;
    private String teacherYear;
}
