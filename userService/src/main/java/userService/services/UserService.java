package userService.services;

import userService.dtos.SignUpRequestDto;
import userService.dtos.UpdateUserDto;
import userService.dtos.reponseDtos.LoginResponseDto;
import userService.dtos.reponseDtos.OtpResponseDto;
import userService.dtos.reponseDtos.UserResponseDto;

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

