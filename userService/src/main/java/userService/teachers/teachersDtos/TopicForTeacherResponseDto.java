package userService.teachers.teachersDtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import userService.students.modals.StudentAndSubject;
import userService.students.stDto.SelectSubjectAndStudentDetailsResponseDto;

@Getter
@Setter
public class TopicForTeacherResponseDto {
    private long topicId;
    private long teacherId;
    private SelectSubjectAndStudentDetailsResponseDto responseDto;
    private String topic;
}
