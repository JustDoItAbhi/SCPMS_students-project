package userService.registrations.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import userService.students.modals.Students;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public class Users extends BaseModels{
    private String name;
    private String email;
    private String password;
    private String address;
    @ManyToMany(fetch = FetchType.EAGER,cascade =
            {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Roles>rolesList;
    private String otp;
    private LocalDateTime otpExpiry;
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Students studentProfile;
}
