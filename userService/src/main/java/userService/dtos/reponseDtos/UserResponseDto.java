package userService.dtos.reponseDtos;

import lombok.Getter;
import lombok.Setter;
import userService.dtos.RolesRequestDto;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private List<RoleResponseDto> rolesList;
}
