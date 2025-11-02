package userService.students.stDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicResponeDto {
    private long teacherId;
    private long studentandSubjectId;
    private String topic;
}
