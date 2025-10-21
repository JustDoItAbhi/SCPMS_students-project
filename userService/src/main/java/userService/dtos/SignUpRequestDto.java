package userService.dtos;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import userService.entitis.Roles;

import java.util.List;
@Getter
@Setter
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;
    private String address;
    private List<String> rolesList;
}
