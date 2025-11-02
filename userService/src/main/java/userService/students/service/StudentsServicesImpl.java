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
import userService.students.mapper.SubjectAndStudentMapper;
import userService.students.modals.StudentAndSubject;
import userService.students.modals.StudentTopic;
import userService.students.modals.Students;
import userService.students.stDto.*;
import userService.students.studentRepo.StudentSubjectRepo;
import userService.students.studentRepo.StudentTopicRepo;
import userService.students.studentRepo.StudentsRepository;
import userService.subjects.Subjects;
import userService.subjects.dtos.SubjectResponseDto;
import userService.subjects.repo.SubjectRepository;
import userService.teachers.modal.Teachers;
import userService.teachers.repos.TeacherRepository;
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
    @Autowired
    private StudentSubjectRepo studentSubjectRepo;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentTopicRepo studentTopicRepo;
    @Autowired
    private TeacherRepository teacherRepository;

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

    @Override
    public SelectSubjectAndStudentDetailsResponseDto selectYourSubject(long id, SelectSubjectAndStudentDetailsReqDto dto) {
        Optional<Students>exsitingStudent=studentsRepository.findById(id);
        if(exsitingStudent.isEmpty()){
            throw new UserExceptions("NO SUCH STUDENT EXISTS PLEASE COMPLETE SIGNUP "+id);
        }
        StudentAndSubject studentAndSubject=new StudentAndSubject();
        studentAndSubject.setStudents(exsitingStudent.get());
        Optional<Subjects>exsitingSubject=subjectRepository.findBySubject(dto.getSubject());
        if(exsitingSubject.isEmpty()){
            throw new UserExceptions("NO SUCH SUBJECT EXSITS "+dto.getSubject());
        }
        studentAndSubject.setSubject(dto.getSubject());
        studentAndSubject.setSubjectYear(dto.getSubjectYear());
        studentSubjectRepo.save(studentAndSubject);
        return SubjectAndStudentMapper.selectingFromEntityselectYourSubject(studentAndSubject);
    }

    @Override
    public boolean deleteSelectedSubject(long subjectId) {

        studentSubjectRepo.deleteById(subjectId);
        return true;
    }

    @Override
    public TopicResponeDto submitTopic(TopicRequestDto dto) {
        Optional<Teachers>exsitingTeacher=teacherRepository.findById(dto.getTeacherId());
        if(exsitingTeacher.isEmpty()){
            throw new UserExceptions("PLEASE CHOOSE A VALID TEACHER FOR SUBJECT TOPIC "+dto.getTeacherId());
        }
        Optional<StudentAndSubject>studentAndSubject=studentSubjectRepo.findById(dto.getStudentandSubjectId());
        if(studentAndSubject.isEmpty()){
            throw new UserExceptions("THIS SUBJECT NOT EXSITS  "+dto.getStudentandSubjectId());
        }
        List<Teachers>teachersList=teacherRepository.findBySubject(studentAndSubject.get().getSubject());
        if(teachersList.isEmpty()){
            throw new UserExceptions("TEACHER NOT ASSINGED TO THIS SUBJECT YET PLEASE REQUEST TEACHER "+dto.getTeacherId());
        }
        StudentTopic topic=new StudentTopic();
        topic.setTeacherId(dto.getTeacherId());
        topic.setStudentandSubjectId(dto.getStudentandSubjectId());
        topic.setTopic(dto.getTopic());
        studentTopicRepo.save(topic);
        return SubjectAndStudentMapper.fromTopicEntity(topic);
    }

    @Override
    public StudentsResponseDto getStudentById(long id) {
        Optional<Users>existingUser=userRepository.findById(id);
        if(existingUser.isEmpty()){
            throw new UserExceptions("NO SUCH USER "+id);
        }
        Users users=existingUser.get();
        Optional<Students>students=studentsRepository.findByUserId(id);
        if(students.isEmpty()){
            throw new UserExceptions("NO SUCH STUDENT "+id);
        }
        return StudentsMapper.fromStudents(students.get());
    }



    @Override
    public SelectSubjectAndStudentDetailsResponseDto getSubjectandStudentById(long userId) {
        Optional<StudentAndSubject>subjectsandSubject=studentSubjectRepo.findByUserId(userId);
        if(subjectsandSubject.isEmpty()){
            throw new UserExceptions("NO SUCH SUBJECT EXSISTS "+userId);
        }

        return SubjectAndStudentMapper.selectingFromEntityselectYourSubject(subjectsandSubject.get());
    }

    @Override
    public Boolean deleteFullUserById(long userId) {
        // Delete all StudentAndSubject records first
        studentSubjectRepo.deleteByUserId(userId);

        // Then delete other related entities...
        studentsRepository.deleteByUserId(userId);

        // Finally delete user
        userRepository.deleteById(userId);
        return null;
    }


}
