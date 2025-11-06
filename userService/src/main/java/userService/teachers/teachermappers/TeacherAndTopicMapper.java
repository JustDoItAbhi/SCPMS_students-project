package userService.teachers.teachermappers;

import userService.teachers.modal.TeacherAndTopics;
import userService.teachers.teachersDtos.TeacherTopicResponseDto;

public class TeacherAndTopicMapper {
    public static TeacherTopicResponseDto fromTeacherTopicEntity(TeacherAndTopics teacherAndTopics){
        TeacherTopicResponseDto dto=new TeacherTopicResponseDto();
        dto.setTopicStatus(teacherAndTopics.getTopicStatus());
        dto.setTeacherTopicId(teacherAndTopics.getId());
        dto.setTeacherId(teacherAndTopics.getTeacherId());
        dto.setTopicId(teacherAndTopics.getTopicId());
        dto.setStudentSubjectId(teacherAndTopics.getStudentSubjectId());
        dto.setTopic(teacherAndTopics.getTopic());
        return dto;
    }
}
