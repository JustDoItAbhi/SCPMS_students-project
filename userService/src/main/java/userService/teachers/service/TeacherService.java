package userService.teachers.service;

import userService.students.stDto.TopicResponeDto;
import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;
import userService.teachers.teachersDtos.TopicForTeacherResponseDto;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto completeSignup(long id,TeacherRequestDto dto);
    List<TeacherForStudentsResponseDto> listOfTeachersBySubject(String subject);
    boolean deteteTeacher(long id);
    List<TopicForTeacherResponseDto> getAllTheTopicRequestByTeacherId(long teacherId);
}
