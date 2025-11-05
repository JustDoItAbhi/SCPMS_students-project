package userService.students.stDto;

import lombok.Getter;
import lombok.Setter;
import userService.students.modals.enums.TOPIC_STATUS;


@Getter
@Setter
public class TopicResponeDto {
    private long teacherId;
    private SelectSubjectAndStudentDetailsResponseDto selectSubjectAndStudentDetailsResponseDto;
    private String topic;
    private TOPIC_STATUS aprovels;
}
