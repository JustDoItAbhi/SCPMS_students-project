package userService.teachers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.UserRepository;
import userService.students.mapper.SubjectAndStudentMapper;
import userService.students.modals.StudentAndSubject;
import userService.students.modals.StudentTopic;
import userService.students.modals.enums.TOPIC_STATUS;
import userService.students.studentRepo.StudentSubjectRepo;
import userService.students.studentRepo.StudentTopicRepo;
import userService.subjects.Subjects;
import userService.subjects.repo.SubjectRepository;
import userService.teachermappers.TeacherAndTopicMapper;
import userService.teachermappers.TeacherMapper;
import userService.teachers.modal.TeacherAndTopics;
import userService.teachers.modal.Teachers;
import userService.teachers.repos.TeacherAndTopicsRepository;
import userService.teachers.repos.TeacherRepository;
import userService.teachers.teachersDtos.*;

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
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherAndTopicsRepository teacherAndTopicsRepository;
    @Autowired
    private StudentSubjectRepo studentSubjectRepo;

    @Override
    public TeacherResponseDto completeSignup(long userid, String subject) {
        System.out.println("SUBJECT FROM DTO "+subject);
        Optional<Users> existingUsers=userRepository.findById(userid);
        if(existingUsers.isEmpty()){
            throw new UserExceptions("USER NOT EXISTS "+userid);
        }
        Users users= existingUsers.get();
        Optional<Teachers>findByTeacherEmail=teacherRepository.findByUserEmail(existingUsers.get().getEmail());
        if(findByTeacherEmail.isPresent()){
            throw new UserExceptions("YOU ARE NOT ALLOWED TO ENROL FOR ANOTHOR SUBJECT "+existingUsers.get().getEmail());
        }
        boolean isTeacher=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("TEACHER"));
        boolean isAdmin=users.getRolesList().stream()
                .anyMatch(roles -> roles.getRoleName().equalsIgnoreCase("ADMIN"));
        if(!isTeacher && !isAdmin){
            throw new UserExceptions("YOU ARE NOT A STUDENT NIGTHER A TEACHER SO NOT ALLOW FOR THIS REQUEST ");
        }
        Teachers teachers=new Teachers();
        Optional<Subjects>subjects=subjectRepository.findBySubject(subject);
        if(subjects.isEmpty()){
            throw new UserExceptions("SUBJECT IS NOT AVAILABLE PLEASE TRY ANOTHER SUBJECT "+subject);
        }
        System.out.println("SUBJECT ----------"+subjects.get().getSubject()+" YEAR "+subjects.get().getCourseYear());
        teachers.setSubject(subjects.get().getSubject());
        teachers.setTeacherYear(subjects.get().getCourseYear());
        teachers.setUsers(existingUsers.get());
        teachers.setTeacherName(existingUsers.get().getName());
        teachers.setUserEmail(existingUsers.get().getEmail());
        teacherRepository.save(teachers);
        return TeacherMapper.fromTeacherEntity(teachers);
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
        Optional<Subjects>subjects=subjectRepository.findBySubject(subject);
        if(subjects.isEmpty()){
            throw new UserExceptions("SUBJECT IS NOT AVAILABLE PLEASE TRY ANOTHER SUBJECT "+subject);
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

    @Override
    public UpdateTeacherResponeDto updateTeacher(long id,long userId, TeacherRequestDto dto) {
        Optional<Teachers>exsitingTeacher=teacherRepository.findById(id);
        if(exsitingTeacher.isEmpty()){
            throw new UserExceptions("TEACHER ID NOT FOUND");
        }
        Teachers teachers=exsitingTeacher.get();
        Optional<Users>exsitingUser=userRepository.findById(userId);
        if(exsitingUser.isEmpty()){
            throw new UserExceptions("USER ID UNDEFINED "+userId);
        }teachers.setUsers(exsitingUser.get());
        Optional<Subjects>subjects=subjectRepository.findBySubject(dto.getTeacherSubject());
        if(subjects.isEmpty()){
            throw new UserExceptions("PLEASE CHECK THE SUBJECT AGAIN ");
        }
        Subjects subject=subjects.get();
        System.out.println("SUBJECT ----------"+subject.getSubject()+" YEAR "+subject.getCourseYear());
        teachers.setSubject(subject.getSubject());
        teachers.setTeacherYear(subject.getCourseYear());
        teachers.setUserEmail(exsitingUser.get().getEmail());
        teachers.setTeacherName(exsitingUser.get().getName());
        teacherRepository.save(teachers);
        return TeacherMapper.updateMapper(teachers);
    }

    @Override
    public List<ListOfTechersResponseDto> getListOfTeacher() {
        List<Teachers>teachersList=teacherRepository.findAll();
        List<ListOfTechersResponseDto>responseDtoList=new ArrayList<>();
        for(Teachers teachers:teachersList){
            responseDtoList.add(TeacherMapper.getListFromEntity(teachers));
        }
        return responseDtoList;
    }

    @Override
    public TeacherResponseDto getTeacherById(long id) {
        Optional<Teachers>teachersOptional=teacherRepository.findById(id);
        if(teachersOptional.isEmpty()){
            throw new UserExceptions("THIS TEACHER IS SAVED IN DATABASE "+id);
        }
        return TeacherMapper.fromTeacherEntity(teachersOptional.get());
    }

    @Override
    public long getTeacherByUserEmail(String userEmail) {
        Optional<Teachers>byEmail=teacherRepository.findByUserEmail(userEmail);
        if(byEmail.isEmpty()){
            throw new UserExceptions("TEACHER EMAIL NOT EXSISTS");
        }
        long ticherId=byEmail.get().getId();
        return ticherId;
    }

    @Override
    public TeacherTopicResponseDto saveTopicwhichIsApproved(TeacherTopicRequestDto dto) {
        Optional<StudentTopic>studentTopic=studentTopicRepo.findById(dto.getTopicId());
        if(studentTopic.isEmpty()){
            throw new UserExceptions("NO SUCH TOPIC AVAILABLE "+dto.getTopic());
        }
        StudentTopic studentTopic1=studentTopic.get();
        Optional<StudentAndSubject>studentAndSubject=studentSubjectRepo.findById(dto.getStudentSubjectId());
        if(studentAndSubject.isEmpty()){
            throw new UserExceptions("STUDENT SUBJECT ID IS INVALID "+dto.getStudentSubjectId());
        }
        TeacherAndTopics teacherAndTopics=new TeacherAndTopics();
        if(dto.getTopicStatus().equalsIgnoreCase("APPROVED")) {
            teacherAndTopics.setTopicStatus(TOPIC_STATUS.APPROVED);
        }else if (dto.getTopicStatus().equalsIgnoreCase("REJECTED")){
            teacherAndTopics.setTopicStatus(TOPIC_STATUS.REJECTED);
        };
        teacherAndTopics.setTopicId(dto.getTopicId());
        teacherAndTopics.setTopic(dto.getTopic());
        teacherAndTopics.setTeacherId(dto.getTeacherId());
        teacherAndTopics.setStudentSubjectId(dto.getStudentSubjectId());
        teacherAndTopicsRepository.save(teacherAndTopics);
        StudentTopic studentTopic2=studentTopic.get();
        studentTopic2.setTeacherAprovels(teacherAndTopics.getTopicStatus());
        studentTopicRepo.save(studentTopic2);
        return TeacherAndTopicMapper.fromTeacherTopicEntity(teacherAndTopics);
    }

    @Override
    public long getListOfTopicsApprovedByTeacher(String approved) {
        List<TeacherAndTopics>topics=teacherAndTopicsRepository.findByTopicStatus(TOPIC_STATUS.valueOf(approved));
        if(topics.isEmpty()){
            throw new UserExceptions("NO SUCH TOPIC AVAILABLE "+approved);
        }
        long topicLength=topics.size();
        return topicLength;
    }

    private TeacherForStudentsResponseDto forStudents(Teachers teachers){
        TeacherForStudentsResponseDto dto=new TeacherForStudentsResponseDto();
        dto.setSubject(teachers.getSubject());
        dto.setTeacherId(teachers.getId());
        dto.setTeacherEmail(teachers.getUsers().getEmail());
        return dto;
    }


}
