package userService.services;

import org.springframework.stereotype.Service;
import userService.dtos.RolesRequestDto;
import userService.dtos.reponseDtos.RoleResponseDto;
import userService.entitis.Roles;
import userService.rpos.RolesRepository;

import java.util.Optional;

@Service
public class RoleServicesImpl implements RoleService{
    private final RolesRepository rolesRepository;
    public RoleServicesImpl (RolesRepository repository){
        rolesRepository=repository;
    }


    @Override
    public RoleResponseDto createRoles(RolesRequestDto dto) {
        Optional<Roles>exsitingRoles=rolesRepository.findByRoleName(dto.getRoles());
        if(exsitingRoles.isPresent()){
            throw new RuntimeException("role already exists"+dto.getRoles());
        }
        Roles roles=new Roles();
        roles.setRoleName(dto.getRoles());
        rolesRepository.save(roles);
        return roleMapper(roles);
    }

    private RoleResponseDto roleMapper(Roles roles){
        RoleResponseDto dto=new RoleResponseDto();
        dto.setRoles(roles.getRoleName());
        return dto;
    }
}
