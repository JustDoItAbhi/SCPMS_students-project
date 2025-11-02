package userService.students.service;

import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.students.stDto.*;
import userService.subjects.dtos.SubjectResponseDto;

public interface StudentsService {
    StudentsResponseDto finishSignUp(long stId, StudentRequestDto dto);
    boolean deleteStd(long id);
    SelectSubjectAndStudentDetailsResponseDto selectYourSubject(long id,SelectSubjectAndStudentDetailsReqDto dto);
    boolean deleteSelectedSubject(long subjectId);
    TopicResponeDto submitTopic(TopicRequestDto dto);
    StudentsResponseDto getStudentById(long id);
    SelectSubjectAndStudentDetailsResponseDto  getSubjectandStudentById(long userId);
    Boolean deleteFullUserById(long userId);

// find user by id , create a post api to find user by id , and add year and add subject and send response dto with user details and subject and year of subject
//AFTER THIS LET USER WILL FIND SUBJECT AND SELECT TEACHER
    // THEN THEN WRITE THE TOPIC


}
