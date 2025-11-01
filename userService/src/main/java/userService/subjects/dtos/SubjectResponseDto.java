package userService.subjects.dtos;

import lombok.Getter;
import lombok.Setter;
import userService.subjects.Subjects;

import java.util.List;

@Getter
@Setter
public class SubjectResponseDto {
    private String courseYear;
    private List<String>  subjectsList;
}
