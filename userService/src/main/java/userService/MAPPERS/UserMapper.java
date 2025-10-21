package userService.MAPPERS;

import userService.dtos.reponseDtos.RoleResponseDto;
import userService.dtos.reponseDtos.UserResponseDto;
import userService.entitis.Roles;
import userService.entitis.Users;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {
    public static UserResponseDto fromUserEntity(Users users){
        UserResponseDto responseDto=new UserResponseDto();
        responseDto.setId(users.getId());
        responseDto.setName(users.getName());
        responseDto.setEmail(users.getEmail());
        responseDto.setPassword(users.getPassword());
        responseDto.setAddress(users.getAddress());
        List<RoleResponseDto>rolesList=new ArrayList<>();
        for(Roles roles: users.getRolesList()){
           RoleResponseDto dto=new RoleResponseDto();
           dto.setRoles(roles.getRoleName());
            rolesList.add(dto);
        }

        responseDto.setRolesList(rolesList);
        return responseDto;
    }
}
