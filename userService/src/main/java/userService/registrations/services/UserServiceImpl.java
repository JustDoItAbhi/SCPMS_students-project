package userService.registrations.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import userService.registrations.dtos.teacherDto.TeacherUserRequestDto;
import userService.registrations.dtos.teacherDto.TeacherUserResponseDto;
import userService.registrations.entities.StudentOtp;
import userService.registrations.entities.TeachrUser;
import userService.registrations.mappers.UserMapper;
import userService.registrations.dtos.SignUpRequestDto;
import userService.registrations.dtos.UpdateUserDto;
import userService.registrations.dtos.reponseDtos.LoginResponseDto;
import userService.registrations.dtos.reponseDtos.OtpResponseDto;
import userService.registrations.dtos.reponseDtos.UserResponseDto;
import userService.registrations.entities.Roles;
import userService.registrations.entities.Users;
import userService.registrations.exceptions.UserExceptions;
import userService.registrations.java_email.EmailService;
import userService.registrations.repos.RolesRepository;
import userService.registrations.repos.StudentOtpRepository;
import userService.registrations.repos.TeacherUserRepository;
import userService.registrations.repos.UserRepository;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private EmailService emailService;
    @Autowired
    private StudentOtpRepository studentOtpRepository;
    @Autowired
    private TeacherUserRepository teacherUserRepository;


    @Override
    public String studentSignup(String email, String role) {
        Optional<Roles>roles=rolesRepository.findByRoleName(role);
        if(roles.isEmpty()){
            throw new UserExceptions("NO SUCH ROLE EXSITS "+role);
        }
        if(roles.get().getRoleName().equals("STUDENT")){
            boolean isOtpSent=sendOtpToUserSignUp(email);

            if(!isOtpSent){
                throw new UserExceptions("OTP NOT SET YET ");
            }else {
                return "STUDENTS";
            }
        }else if (roles.get().getRoleName().equals("APPLICANT_TEACHER")){
            emailService.sendSimpleEmail("arvinderpalsingh2321@gmail.com","REQUEST FOR SIGN UP FROM TEACHER ",
                    "ALLOW TEACHER TO SIGN UP "+email+" THANK YOUR");
            System.out.println("EMAIL SENT FOR SIGNUP TEACHER");
            return "PLEASE WAIT";
        }else{
            throw new UserExceptions("YOU ARE NOT ALLOWD TO ACCESS ");
        }

    }
    private boolean sendOtpToUserSignUp(String email){

        OtpResponseDto responseDto=new OtpResponseDto();
        SecureRandom random = new SecureRandom();
        int randomOtp = random.nextInt(1000000);
        String otp =  String.format("%06d", randomOtp);
        LocalDateTime generatedTime = LocalDateTime.now();
        LocalDateTime expiryTime = generatedTime.plusMinutes(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String generatedTimeReadable = generatedTime.format(formatter);
        String expiryTimeReadable = expiryTime.format(formatter);
//        users.setOtp(passwordEncoder.encode( otp));
//        users.setOtpExpiry(expiryTime);
        responseDto.setEmail(email);
        responseDto.setOtp(otp);
        System.out.println("HERE IS OTP "+otp);
        responseDto.setExpiryTime(expiryTime);

        Optional<StudentOtp>oldstudentOtp=studentOtpRepository.findByEmail(email);
        if(oldstudentOtp.isPresent()){
            StudentOtp studentOtp= oldstudentOtp.get();
            studentOtp.setEmail(email);
            studentOtp.setOtp(passwordEncoder.encode(otp));
            studentOtp.setExpiryTime(expiryTime);
            studentOtpRepository.save(studentOtp);
        }else {
            StudentOtp studentOtp= new StudentOtp();
            studentOtp.setEmail(email);
            studentOtp.setOtp(passwordEncoder.encode(otp));
            studentOtp.setExpiryTime(expiryTime);
            studentOtpRepository.save(studentOtp);
        }

        if(generatedTime.isAfter(expiryTime)){
            throw new UserExceptions("OTP expired");
        }
        emailService.sendSimpleEmail(email,"YOUR OTP IS "+ otp, "THANK YOUR");
        System.out.println("EMAIL SENT FOR SIGNUP ");


//        userRepository.save(users);
        return true;
    }
    @Override
    public String confirmStudentOtp(String email, String otp) {
        Optional<StudentOtp>studentOtp=studentOtpRepository.findByEmail(email);
        if(studentOtp.isEmpty()){
            throw new UserExceptions("EMAIL NOT EXISTS "+email);
        }

        StudentOtp student=studentOtp.get();
        if(!passwordEncoder.matches(otp,student.getOtp())){
            throw new UserExceptions("INVALID OTP "+ otp+" Request "+student.getEmail());
        }else if(LocalDateTime.now().isAfter( student.getExpiryTime())){
            throw new UserExceptions("OTP EXPIRED " + otp);
        }
        return "PLEASE SIGN UP NOW ";
    }

    @Override
    public TeacherUserResponseDto approveTeacherSignUp(TeacherUserRequestDto dto) {
        Optional<Users>exsitingUser=userRepository.findByEmail(dto.getRole());
        if(exsitingUser.isPresent()){
            emailService.sendSimpleEmail(dto.getTeacherEmail(),"you already regitered teacher please signup or reset password "+ dto.getRole(), "THANK YOUR FOR REACHING US ");
            System.out.println("EMAIL SENT FOR SIGNUP ");
        }
            Optional<Roles>rolesOptional=rolesRepository.findByRoleName(dto.getRole());
            if(rolesOptional.isEmpty()){
                throw new UserExceptions("NO SUCH ROLE EXSISTS"+dto.getRole());
            }if(!dto.getRole().equals("TEACHER")){
                throw new UserExceptions("YOU ARE NOT A TEACHER "+dto.getRole());
        }
            Optional<TeachrUser>tUser=teacherUserRepository.findByTeacherEmail(dto.getTeacherEmail());
            if(tUser.isPresent()){
                throw new UserExceptions("this teacher already exsists "+ dto.getTeacherEmail());
            }
        TeachrUser teachrUser=new TeachrUser();
            teachrUser.setTeacherEmail(dto.getTeacherEmail());
            teachrUser.setRole("TEACHER");
            teacherUserRepository.save(teachrUser);

            TeacherUserResponseDto teacherUserResponseDto=new TeacherUserResponseDto();
            teacherUserResponseDto.setTeacherEmail(teachrUser.getTeacherEmail());
            teacherUserResponseDto.setRole(teachrUser.getRole());

        return teacherUserResponseDto;
    }


    @Override
    public UserResponseDto createUser(SignUpRequestDto dto) {
        Optional<Users>exsitingUser=userRepository.findByEmail(dto.getEmail());
        if(exsitingUser.isPresent()){
            throw new UserExceptions("USER ALREADY EXISTS "+dto.getEmail());
        }
        Users users=new Users();
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        String password= passwordEncoder.encode(dto.getPassword());
        users.setPassword(password);
        users.setAddress(dto.getAddress());
        List<Roles>rolesList=new ArrayList<>();
        for(String roles:dto.getRolesList()){
            Optional<Roles>rolesOptional=rolesRepository.findByRoleName(roles);
            if(rolesOptional.isEmpty()){
                throw new UserExceptions("NO SUCH ROLE EXSISTS"+roles);
            }

            rolesList.add(rolesOptional.get());
        }
        // if ROLE IS STUDENT THEN SEND OTP TO CONFIM EMAIL AND ADD OTP PAGE IN REACT AND FINISH SIGN UP
        if(dto.getRolesList().equals("STUDENT")) {
            Optional<StudentOtp>studentOtp=studentOtpRepository.findByEmail(dto.getEmail());
            if(studentOtp.isEmpty()){
                throw new UserExceptions("YOU ARE NOT ALLOWED TO SIGNUP PLEASE CONTACT ADMIN :: arvinderpalsingh2321@gmail.com");
            }
        }
        // IF ROLE IS TEACHER THEN SEND EMAIL AND WAIT FOR ADMIN TO CONFIM EMAIL , AND SEND EMAIL TO TEACHER WITH A LINK TO FINISH SIGNUP
        users.setRolesList(rolesList);
        userRepository.save(users);
        UserResponseDto responseDto= UserMapper.fromUserEntity(users);
        emailService.sendSimpleEmail(users.getEmail(),"WELCOME TO S.C.P.M.S MANAGEMENT SYSTSM 😎", "WORK HARD AND ACHIEV YOUR GOALS , ALL THE BEST !!! 👍");
        System.out.println("EMAIL SENT FOR SIGNUP ");
        return responseDto;
    }

    private boolean confimOtp(String email, String Otp) {
        Optional<Users>exsistingUsers=userRepository.findByEmail(email);
        if(exsistingUsers.isEmpty()) {
            throw new UserExceptions("NO USER FOUND "+ email);
        }

        Users users=exsistingUsers.get();
        if(!passwordEncoder.matches(Otp,users.getOtp())){
            throw new UserExceptions("INVALID OTP "+ Otp+" Request "+users.getOtp());
        }else if(LocalDateTime.now().isAfter( users.getOtpExpiry())){
            throw new UserExceptions("OTP EXPIRED " + Otp);
        }
        userRepository.save(users);
        System.out.println("PASSWORD RESET SUCCESSFULLY_________________________________");
        return true;
    }


    @Override
    public LoginResponseDto login(String email, String password) {
        Optional<Users>exsitingUser=userRepository.findByEmail(email);
        if(exsitingUser.isEmpty()){
            throw new UserExceptions("User not exsits please SignUP "+email);
        }
        if(!passwordEncoder.matches(password,exsitingUser.get().getPassword())){
            throw new UserExceptions("User not Password exsits please enter correct password"+password);
        }
        SecretKey key= Keys.hmacShaKeyFor(exsitingUser.get().getPassword().getBytes());
        HashMap<String,String>claims=new HashMap<>();
        claims.put("email",exsitingUser.get().getEmail());
        claims.put("name",exsitingUser.get().getName());

       String token= Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 minutes
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
       LoginResponseDto dto=new LoginResponseDto();
       dto.setToken(token);
       System.out.println("user loged in ");
        return dto;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<Users>users=userRepository.findAll();
        List<UserResponseDto>responseDtoList=new ArrayList<>();
        for(Users users1:users){
            responseDtoList.add(UserMapper.fromUserEntity(users1));
        }
        return responseDtoList;
    }

    @Override
    public UserResponseDto getUserById(long id) {
        Optional<Users> users=userRepository.findById(id);
        if(users.isEmpty()){
            throw new UserExceptions("NO USER WITH THIS ID "+id);
        }
        return UserMapper.fromUserEntity(users.get());
    }

    @Override
    @Transactional
    public boolean deleteUserById(long id) {
    Optional<Users>users=userRepository.findById(id);
    if(users.isPresent()){
        users.get().getRolesList().clear();
        userRepository.save(users.get());
        userRepository.delete(users.get());
        return true;
    }else{
        return  false;
    }

    }

    @Override
    public UserResponseDto updateUser(long id, UpdateUserDto dto) {
        Optional<Users>exsistingUsers=userRepository.findById(id);
        if(exsistingUsers.isEmpty()) {
            throw new UserExceptions("NO USER FOUND"+ id);
        }
        Users users= exsistingUsers.get();
        List<Roles>rolesList=new ArrayList<>();
        for(String roles:dto.getRolesList()){
            Optional<Roles>rolesOptional=rolesRepository.findByRoleName(roles);
            if(rolesOptional.isEmpty()){
                throw new UserExceptions("NO SUCH ROLE EXSISTS"+roles);
            }
            rolesList.add(rolesOptional.get());
        }
        users.setRolesList(rolesList);
        users.setName(dto.getName());
        users.setEmail(dto.getEmail());
        users.setAddress(dto.getAddress());
        users.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(users);
        return UserMapper.fromUserEntity(users);
    }

    @Override
    public OtpResponseDto sendOtp(String email) {
        Optional<Users>exsistingUsers=userRepository.findByEmail(email);
        if(exsistingUsers.isEmpty()) {
            throw new UserExceptions("NO USER FOUND"+ email);
        }
        Users users=exsistingUsers.get();
        OtpResponseDto dto=new OtpResponseDto();
        SecureRandom random = new SecureRandom();
        int randomOtp = random.nextInt(1000000);
        String otp =  String.format("%06d", randomOtp);


        LocalDateTime generatedTime = LocalDateTime.now();
        LocalDateTime expiryTime = generatedTime.plusMinutes(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String generatedTimeReadable = generatedTime.format(formatter);
        String expiryTimeReadable = expiryTime.format(formatter);
        users.setOtp(passwordEncoder.encode( otp));
//                users.setOtp(otp);
        users.setOtpExpiry(expiryTime);
        userRepository.save(users);
        dto.setEmail(email);
        dto.setOtp(otp);
        System.out.println("HERE IS OTP "+otp);
        dto.setExpiryTime(expiryTime);
        if(generatedTime.isAfter(expiryTime)){
            throw new UserExceptions("OTP expired");
        }
        emailService.sendSimpleEmail(users.getEmail(),"YOUR OTP IS "+ otp, "THANK YOUR");
        System.out.println("email sent");
        return dto;
    }



    @Override
    public UserResponseDto resetPassword(String email, String Otp, String password) {
        Optional<Users>exsistingUsers=userRepository.findByEmail(email);
        if(exsistingUsers.isEmpty()) {
            throw new UserExceptions("NO USER FOUND "+ email);
        }

        Users users=exsistingUsers.get();
        if(!passwordEncoder.matches(Otp,users.getOtp())){
            throw new UserExceptions("INVALID OTP "+ Otp+" equest "+users.getOtp());
        }else if(LocalDateTime.now().isAfter( users.getOtpExpiry())){
            throw new UserExceptions("OTP EXPIRED " + Otp);
        }else if(passwordEncoder.matches(password,users.getPassword())){
            throw new UserExceptions("New password cannot be same as old password");

        }
        users.setPassword(passwordEncoder.encode(password));
        userRepository.save(users);
        System.out.println("PASSWORD RESET SUCCESSFULLY_________________________________");
        return UserMapper.fromUserEntity(users);
    }



}
