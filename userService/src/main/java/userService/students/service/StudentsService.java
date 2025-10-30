package userService.students.service;

import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.students.stDto.StudentRequestDto;

public interface StudentsService {
    UserResponseDto finishSignUp(StudentRequestDto dto);
}
