package userService.students.stDto;

import lombok.Getter;
import lombok.Setter;
import userService.students.modals.StudentAndSubject;

@Getter
@Setter
public class SelectSubjectAndStudentDetailsResponseDto {
    private long studentAndSubjectId;
//    private StudentsResponseDto studentsResponseDto;
    private StudentAndSubject subject;
    private String subjectYear;
}
