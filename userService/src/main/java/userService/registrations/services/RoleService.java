package userService.registrations.services;

import userService.registrations.dtos.RolesRequestDto;
import userService.registrations.dtos.reponseDtos.RoleResponseDto;

public interface RoleService {
    RoleResponseDto createRoles(RolesRequestDto dto);
}
