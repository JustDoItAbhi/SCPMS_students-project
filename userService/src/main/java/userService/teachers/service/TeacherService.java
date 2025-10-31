package userService.teachers.service;

import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto completeSignup(long id,TeacherRequestDto dto);
    List<TeacherForStudentsResponseDto> listOfTeachersBySubject(String subject);
}
