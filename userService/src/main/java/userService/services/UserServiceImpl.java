package userService.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import userService.MAPPERS.UserMapper;
import userService.dtos.SignUpRequestDto;
import userService.dtos.UpdateUserDto;
import userService.dtos.reponseDtos.LoginResponseDto;
import userService.dtos.reponseDtos.OtpResponseDto;
import userService.dtos.reponseDtos.UserResponseDto;
import userService.entitis.Roles;
import userService.entitis.Users;
import userService.exceptions.UserExceptions;
import userService.java_email.EmailService;
import userService.rpos.RolesRepository;
import userService.rpos.UserRepository;

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
        users.setRolesList(rolesList);
        userRepository.save(users);
        UserResponseDto responseDto= UserMapper.fromUserEntity(users);

        return responseDto;
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
