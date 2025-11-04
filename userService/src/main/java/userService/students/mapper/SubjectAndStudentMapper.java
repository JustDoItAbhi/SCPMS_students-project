package userService.students.mapper;

import userService.students.modals.StudentAndSubject;
import userService.students.modals.StudentTopic;
import userService.students.stDto.SelectSubjectAndStudentDetailsResponseDto;
import userService.students.stDto.TopicResponeDto;

public class SubjectAndStudentMapper {
    public static TopicResponeDto fromTopicEntity(StudentTopic topic){
        TopicResponeDto dto=new TopicResponeDto();
        dto.setSelectSubjectAndStudentDetailsResponseDto(selectingFromEntityselectYourSubject(topic.getStudentAndSubject()));
        dto.setTeacherId(topic.getTeacherId());
        dto.setTopic(topic.getTopic());
        return dto;
    }

    public static SelectSubjectAndStudentDetailsResponseDto selectingFromEntityselectYourSubject(StudentAndSubject studentAndSubject){
        SelectSubjectAndStudentDetailsResponseDto dto=new SelectSubjectAndStudentDetailsResponseDto();
        dto.setStudentAndSubjectId(studentAndSubject.getId());
        dto.setSubjectYear(studentAndSubject.getSubjectYear());
        dto.setSubject(studentAndSubject);
        return dto;
    }

}
