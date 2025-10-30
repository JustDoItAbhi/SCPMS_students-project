package userService.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.registrations.entities.Roles;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.UserRepository;
import userService.students.stDto.StudentRequestDto;
import userService.students.studentRepo.StudentsRepository;

import java.util.Optional;

@Service
public class StudentsServicesImpl implements StudentsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentsRepository studentsRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public UserResponseDto finishSignUp(StudentRequestDto dto) {
        Optional<Roles> roles=rolesRepository.findByRoleName("STUDENT");
        return null;
    }
}
