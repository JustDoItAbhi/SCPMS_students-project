package userService.services;

import userService.dtos.RolesRequestDto;
import userService.dtos.reponseDtos.RoleResponseDto;

public interface RoleService {
    RoleResponseDto createRoles(RolesRequestDto dto);
}
