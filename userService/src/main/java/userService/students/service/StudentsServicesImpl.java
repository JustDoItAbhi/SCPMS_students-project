package userService.students.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.registrations.entities.Roles;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.mappers.UserMapper;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.UserRepository;
import userService.students.StudentsMapper;
import userService.students.modals.Students;
import userService.students.stDto.StudentRequestDto;
import userService.students.stDto.StudentsResponseDto;
import userService.students.studentRepo.StudentsRepository;
//import userService.students.studentRepo.StudentsRepository;

import java.util.ArrayList;
import java.util.List;
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
    public StudentsResponseDto finishSignUp(long stId, StudentRequestDto dto) {
        Optional<Users> existingUsers=userRepository.findById(stId);
        if(existingUsers.isEmpty()){
            throw new UserExceptions("USER NOT EXISTS "+stId);
        }
        Users users= existingUsers.get();
        boolean isStudent=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("STUDENT"));
        boolean isAdmin=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("ADMIN"));
        if(!isStudent && !isAdmin){
            throw new UserExceptions("YOU ARE NOT A STUDENT NIGTHER A TEACHER SO NOT ALLOW FOR THIS REQUEST ");
        }

        Students students=new Students();
        students.setUser(users);
        students.setCurrentYear(dto.getCurrentYear());
        students.setGroupNumber(dto.getGroupNumber());
        students.setMonitorName(dto.getMonitorName());
        students.setSubGroupNumber(dto.getSubGroupNumber());
        students.setStudentIdCardNumber(dto.getStudentIdCardNumber());
        students.setPassportNumber(dto.getPassportNumber());
        studentsRepository.save(students);
//        users.setStudentProfile(students);
//        userRepository.save(users);
        return StudentsMapper.fromStudents(students);
    }

    @Override
    public boolean deleteStd(long id) {
        studentsRepository.deleteById(id);
        return true;
    }
}
