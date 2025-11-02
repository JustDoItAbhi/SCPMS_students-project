package userService.teachers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.UserRepository;
import userService.students.studentRepo.StudentsRepository;
import userService.teachers.modal.Teachers;
import userService.teachers.repos.TeacherRepository;
import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeachersServiceImpl implements TeacherService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Override
    public TeacherResponseDto completeSignup(long id, TeacherRequestDto dto) {
        Optional<Users> existingUsers=userRepository.findById(id);
        if(existingUsers.isEmpty()){
            throw new UserExceptions("USER NOT EXISTS "+id);
        }
        Users users= existingUsers.get();
        boolean isTeacher=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("TEACHER"));
        boolean isAdmin=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("ADMIN"));
        if(!isTeacher && !isAdmin){
            throw new UserExceptions("YOU ARE NOT A STUDENT NIGTHER A TEACHER SO NOT ALLOW FOR THIS REQUEST ");
        }
        Teachers teachers=new Teachers();
        teachers.setSubject(dto.getTeacherSubject());
        teachers.setUsers(existingUsers.get());
        teacherRepository.save(teachers);
        return fromTeacherEntity(teachers);
    }

    @Override
    public List<TeacherForStudentsResponseDto> listOfTeachersBySubject(String subject) {
        List<Teachers>bySubjectTeacher=teacherRepository.findBySubject(subject);
        if(bySubjectTeacher.isEmpty()){
            throw new UserExceptions("NO TEACHER SELECTED THIS TOPIC YET PLEASE TRY AGAIN LATER "+subject);
        }
        List<TeacherForStudentsResponseDto>responseDtos=new ArrayList<>();
        for(Teachers teachers:bySubjectTeacher){
            responseDtos.add(forStudents(teachers));
        }
        return responseDtos;
    }

    @Override
    public boolean deteteTeacher(long id) {
        teacherRepository.deleteById(id);
        return true;
    }

    private TeacherForStudentsResponseDto forStudents(Teachers teachers){
        TeacherForStudentsResponseDto dto=new TeacherForStudentsResponseDto();
        dto.setSubject(teachers.getSubject());
        dto.setTeacherEmail(teachers.getUsers().getEmail());
        return dto;
    }

    private TeacherResponseDto fromTeacherEntity(Teachers teachers){
        TeacherResponseDto dto=new TeacherResponseDto();
        dto.setSubject(teachers.getSubject());
        dto.setUsers(teachers.getUsers());
        /// wait for aproval
        return dto;
    }
}
