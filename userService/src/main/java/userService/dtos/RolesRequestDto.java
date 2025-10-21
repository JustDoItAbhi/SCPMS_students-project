package userService.dtos;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import userService.entitis.Roles;
import userService.entitis.Users;

import java.util.List;

@Getter
@Setter
public class RolesRequestDto {
    private String roles;
}
