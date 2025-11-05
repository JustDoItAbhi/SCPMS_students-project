package userService.teachers.service;

import userService.teachers.teachersDtos.*;

import java.util.List;

public interface TeacherService {
    TeacherResponseDto completeSignup(long id,String subject);
    List<TeacherForStudentsResponseDto> listOfTeachersBySubject(String subject);
    boolean deteteTeacher(long id);
    List<TopicForTeacherResponseDto> getAllTheTopicRequestByTeacherId(long teacherId);
    UpdateTeacherResponeDto updateTeacher(long id, long userId,TeacherRequestDto dto);
    List<ListOfTechersResponseDto> getListOfTeacher();
    TeacherResponseDto getTeacherById(long id);
    long getTeacherByUserEmail(String userEmail);
}
