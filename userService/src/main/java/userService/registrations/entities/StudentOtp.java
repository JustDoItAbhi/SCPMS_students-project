package userService.registrations.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StudentOtp extends  BaseModels{
    private String email;
    private String otp;
    private LocalDateTime expiryTime;
}
