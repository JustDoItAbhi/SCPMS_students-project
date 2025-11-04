package userService.teachers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.UserRepository;
import userService.students.mapper.SubjectAndStudentMapper;
import userService.students.modals.StudentTopic;
import userService.students.stDto.SelectSubjectAndStudentDetailsResponseDto;
import userService.students.stDto.TopicResponeDto;
import userService.students.studentRepo.StudentTopicRepo;
import userService.students.studentRepo.StudentsRepository;
import userService.teachers.TeacherMapper;
import userService.teachers.modal.Teachers;
import userService.teachers.repos.TeacherRepository;
import userService.teachers.teachersDtos.TeacherForStudentsResponseDto;
import userService.teachers.teachersDtos.TeacherRequestDto;
import userService.teachers.teachersDtos.TeacherResponseDto;
import userService.teachers.teachersDtos.TopicForTeacherResponseDto;

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
    @Autowired
    private StudentTopicRepo studentTopicRepo;
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
        String cleanedSubject = cleanSubjectName(subject);
        List<Teachers>bySubjectTeacher=teacherRepository.findBySubjectIgnoreCaseAndTrim(cleanedSubject);
        if(bySubjectTeacher.isEmpty()){
            bySubjectTeacher = teacherRepository.findBySubjectContainingIgnoreCaseAndTrim(cleanedSubject);
        }
        if(bySubjectTeacher.isEmpty()){
            throw new UserExceptions("NO TEACHER SELECTED THIS TOPIC YET PLEASE TRY AGAIN LATER "+subject);
        }
        List<TeacherForStudentsResponseDto>responseDtos=new ArrayList<>();
        for(Teachers teachers:bySubjectTeacher){
            responseDtos.add(forStudents(teachers));
        }
        return responseDtos;
    }
    private String cleanSubjectName(String subject) {
        if (subject == null) return "";

        return subject.trim()
                .toLowerCase()
                .replaceAll("\\s+", " ") // Replace multiple spaces with single space
                .trim();
    }
    @Override
    public boolean deteteTeacher(long id) {
        teacherRepository.deleteById(id);
        return true;
    }

    @Override
    public List<TopicForTeacherResponseDto> getAllTheTopicRequestByTeacherId(long teacherId) {
        List<StudentTopic>bySubjectTeacher=studentTopicRepo.findByTeacherId(teacherId);
        if(bySubjectTeacher.isEmpty()){
            throw new UserExceptions("NO NEW TOPIC REQUEST YET");
        }
        List<TopicForTeacherResponseDto>responeDtos=new ArrayList<>();
        for (StudentTopic studentTopic : bySubjectTeacher) {
            TopicForTeacherResponseDto dto=new TopicForTeacherResponseDto();
            dto.setTopicId(studentTopic.getId());
            dto.setTeacherId(studentTopic.getTeacherId());
            dto.setTopic(studentTopic.getTopic());
            dto.setResponseDto(SubjectAndStudentMapper.selectingFromEntityselectYourSubject(studentTopic.getStudentAndSubject()));
            responeDtos.add(dto);
        }
        return responeDtos;
    }

    private TeacherForStudentsResponseDto forStudents(Teachers teachers){
        TeacherForStudentsResponseDto dto=new TeacherForStudentsResponseDto();
        dto.setSubject(teachers.getSubject());
        dto.setTeacherId(teachers.getId());
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
