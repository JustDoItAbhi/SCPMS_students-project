package userService.students.stDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponeDto {
    private long teacherId;
    private SelectSubjectAndStudentDetailsResponseDto selectSubjectAndStudentDetailsResponseDto;
    private String topic;
}
