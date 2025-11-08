package userService.teachers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.java_email.EmailService;
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
import userService.teachers.teachermappers.TeacherAndTopicMapper;
import userService.teachers.teachermappers.TeacherMapper;
import userService.teachers.modal.TeacherAndTopics;
import userService.teachers.modal.Teachers;
import userService.teachers.repos.TeacherAndTopicsRepository;
import userService.teachers.repos.TeacherRepository;
import userService.teachers.teachersDtos.*;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private EmailService emailService;

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
        // Validate topic exists
        StudentTopic studentTopic1 = studentTopicRepo.findById(dto.getTopicId())
                .orElseThrow(() -> new UserExceptions("NO SUCH TOPIC AVAILABLE " + dto.getTopicId()));

        // Validate student subject exists
        StudentAndSubject studentAndSubject = studentSubjectRepo.findById(dto.getStudentSubjectId())
                .orElseThrow(() -> new UserExceptions("STUDENT SUBJECT ID IS INVALID " + dto.getStudentSubjectId()));

        // Extract student information for email
        String email = studentTopic1.getStudentAndSubject().getStudents().getUser().getEmail();
        String name = studentTopic1.getStudentAndSubject().getStudents().getUser().getName();
        long studentId = studentTopic1.getStudentAndSubject().getStudents().getUser().getId();
        String course = studentTopic1.getStudentAndSubject().getStudents().getCurrentYear();
        String groupNumber = studentTopic1.getStudentAndSubject().getStudents().getGroupNumber();
        String subGroupNumber = studentTopic1.getStudentAndSubject().getStudents().getSubGroupNumber();
        String chosenSubject = studentTopic1.getStudentAndSubject().getSubject();
        String subjectChosenYear = studentTopic1.getStudentAndSubject().getSubjectYear();
        String chosenTopic = studentTopic1.getTopic();

        // Check if teacher topic already exists
        Optional<TeacherAndTopics> teacherByTopicId = teacherAndTopicsRepository.findByTopicId(dto.getTopicId());
        TeacherAndTopics teacherAndTopics;

        if (teacherByTopicId.isPresent()) {
            teacherAndTopics = teacherByTopicId.get();
            // Update status if different
            if (!teacherAndTopics.getTopicStatus().equals(dto.getTopicStatus())) {
                teacherAndTopics.setTopicStatus(mapTopicStatus(dto.getTopicStatus()));
            }
        } else {
            // Create new teacher topic
            teacherAndTopics = new TeacherAndTopics();
            teacherAndTopics.setTopicStatus(mapTopicStatus(dto.getTopicStatus()));
            teacherAndTopics.setTopicId(dto.getTopicId());
            teacherAndTopics.setTopic(dto.getTopic());
            teacherAndTopics.setTeacherId(dto.getTeacherId());
            teacherAndTopics.setStudentSubjectId(dto.getStudentSubjectId());
        }

        // Save teacher topic
        teacherAndTopicsRepository.save(teacherAndTopics);

        // Update student topic status
        studentTopic1.setTeacherAprovels(teacherAndTopics.getTopicStatus());
        studentTopicRepo.save(studentTopic1);

        // Send email only for APPROVED topics
        if (TOPIC_STATUS.APPROVED.equals(teacherAndTopics.getTopicStatus())) {
            try {
                String subject = "Congratulations! Your Topic Has Been Approved";
                String htmlContent = buildHtmlApprovalEmail(name, studentId, course, groupNumber,
                        subGroupNumber, chosenSubject, subjectChosenYear, chosenTopic);

                emailService.sendSimpleEmail(email, subject, htmlContent);
                System.out.println("EMAIL SENT successfully to: " + email);
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
                // Don't throw exception - email failure shouldn't break the main functionality
            }
        }

        System.out.println("Topic approval process completed for teacher");

        return TeacherAndTopicMapper.fromTeacherTopicEntity(teacherAndTopics);
    }

    // Helper method to map string to enum
    private TOPIC_STATUS mapTopicStatus(String topicStatus) {
        if (topicStatus.equalsIgnoreCase("APPROVED")) {
            return TOPIC_STATUS.APPROVED;
        } else if (topicStatus.equalsIgnoreCase("REJECTED")) {
            return TOPIC_STATUS.REJECTED;
        } else {
            throw new UserExceptions("Invalid topic status: " + topicStatus);
        }
    }

    private String buildHtmlApprovalEmail(String name, long studentId, String course,
                                          String groupNumber, String subGroupNumber,
                                          String chosenSubject, String subjectChosenYear,
                                          String chosenTopic) {

        return String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "    <style>" +
                        "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                        "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                        "        .header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }" +
                        "        .content { background: #f9f9f9; padding: 20px; border-radius: 0 0 8px 8px; }" +
                        "        .details { background: white; padding: 15px; margin: 15px 0; border-left: 4px solid #667eea; }" +
                        "        .footer { margin-top: 20px; padding-top: 20px; border-top: 1px solid #ddd; color: #666; }" +
                        "    </style>" +
                        "</head>" +
                        "<body>" +
                        "    <div class='container'>" +
                        "        <div class='header'>" +
                        "            <h1>🎉 Topic Approval Notification</h1>" +
                        "        </div>" +
                        "        <div class='content'>" +
                        "            <h2>Dear %s,</h2>" +
                        "            <p><strong>Congratulations!</strong> Your topic submission has been approved by the administration.</p>" +
                        "            " +
                        "            <div class='details'>" +
                        "                <h3>📋 Student Information</h3>" +
                        "                <p><strong>Student ID:</strong> %d</p>" +
                        "                <p><strong>Name:</strong> %s</p>" +
                        "                <p><strong>Course/Year:</strong> %s</p>" +
                        "                <p><strong>Group:</strong> %s</p>" +
                        "                <p><strong>Sub Group:</strong> %s</p>" +
                        "            </div>" +
                        "            " +
                        "            <div class='details'>" +
                        "                <h3>📚 Subject Details</h3>" +
                        "                <p><strong>Subject:</strong> %s</p>" +
                        "                <p><strong>Academic Year:</strong> %s</p>" +
                        "                <p><strong>Approved Topic:</strong> <em>%s</em></p>" +
                        "            </div>" +
                        "            " +
                        "            <h3>🚀 Next Steps</h3>" +
                        "            <ol>" +
                        "                <li>You can now proceed with your research/work on the approved topic</li>" +
                        "                <li>Contact your supervisor for further guidance</li>" +
                        "                <li>Ensure you meet all the deadlines for your submissions</li>" +
                        "            </ol>" +
                        "            " +
                        "            <p>If you have any questions or need further clarification, please don't hesitate to contact your department.</p>" +
                        "            " +
                        "            <div class='footer'>" +
                        "                <p><strong>Best regards,</strong><br>Academic Administration Team<br>Uzhhorod National University</p>" +
                        "            </div>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>",
                name, studentId, name, course, groupNumber, subGroupNumber,
                chosenSubject, subjectChosenYear, chosenTopic
        );
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

    @Override
    public String deleteTopicByTeacher(long topicId) {
        Optional<TeacherAndTopics>topics=teacherAndTopicsRepository.findById(topicId);
        if(topics.isEmpty()){
            throw new UserExceptions("NO SUCH TOPIC EXISTS "+ topicId);
        }
        teacherAndTopicsRepository.deleteById(topicId);

//            List<Long>idsDelete= Arrays.asList(teacherId);
//            teacherAndTopicsRepository.deleteAllById(Arrays.asList(teacherId));
            return "TOPIC DELETED";

    }

    @Override
    public TeacherTopicResponseDto getTeacherTopicByTopicId(long teacherTopicid) {
        Optional<TeacherAndTopics>topics=teacherAndTopicsRepository.findById(teacherTopicid);
        if(topics.isEmpty()){
            throw new UserExceptions("NO SUCH TOPIC EXSITS");
        }

        return TeacherAndTopicMapper.fromTeacherTopicEntity(topics.get());
    }

    @Override
    public List<TeacherTopicResponseDto> getAllTopicByTeacherId(long teacherId) {
        List<TeacherAndTopics>topics=teacherAndTopicsRepository.findByTeacherId(teacherId);
        if(topics.isEmpty()){
            throw new UserExceptions("NO SUCH TOPIC EXSITS "+teacherId);
        }
        List<TeacherTopicResponseDto>responseDtos=new ArrayList<>();
        for(TeacherAndTopics teacherAndTopics:topics){
            responseDtos.add(TeacherAndTopicMapper.fromTeacherTopicEntity(teacherAndTopics));
        }

        return responseDtos;
    }

    private TeacherForStudentsResponseDto forStudents(Teachers teachers){
        TeacherForStudentsResponseDto dto=new TeacherForStudentsResponseDto();
        dto.setSubject(teachers.getSubject());
        dto.setTeacherId(teachers.getId());
        dto.setTeacherEmail(teachers.getUsers().getEmail());
        return dto;
    }


}
