package userService.students.service;

import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.students.stDto.StudentRequestDto;
import userService.students.stDto.StudentsResponseDto;

public interface StudentsService {
    StudentsResponseDto finishSignUp(long stId, StudentRequestDto dto);
    boolean deleteStd(long id);
}
