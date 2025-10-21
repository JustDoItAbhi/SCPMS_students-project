package userService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import userService.dtos.RolesRequestDto;
import userService.dtos.reponseDtos.RoleResponseDto;
import userService.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
private RoleService roleService;
@PostMapping("/createRole")
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RolesRequestDto dto){
    return ResponseEntity.ok(roleService.createRoles(dto));
}

}
