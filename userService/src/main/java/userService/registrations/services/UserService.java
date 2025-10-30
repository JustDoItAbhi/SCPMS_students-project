package userService.registrations.services;

import userService.registrations.dtos.SignUpRequestDto;
import userService.registrations.dtos.UpdateUserDto;
import userService.registrations.dtos.reponseDtos.LoginResponseDto;
import userService.registrations.dtos.reponseDtos.OtpResponseDto;
import userService.registrations.dtos.reponseDtos.UserResponseDto;

import java.util.List;

public interface UserService {
UserResponseDto createUser(SignUpRequestDto dto);
LoginResponseDto login(String email, String password);
List<UserResponseDto> getAllUsers();
UserResponseDto getUserById(long id);
boolean deleteUserById(long id);
UserResponseDto updateUser (long id, UpdateUserDto dto);
OtpResponseDto sendOtp(String email);
UserResponseDto resetPassword(String email, String Otp,String password);
}

