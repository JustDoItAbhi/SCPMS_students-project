package userService.registrations.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import userService.registrations.entities.StudentOtp;

import java.util.Optional;

@Repository
public interface StudentOtpRepository extends JpaRepository<StudentOtp ,Long> {
    Optional<StudentOtp> findByOtp(String otp);
    Optional<StudentOtp>findByEmail(String email);

}
