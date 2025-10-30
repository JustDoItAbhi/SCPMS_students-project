package userService.registrations.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Roles extends  BaseModels{
    private String roleName;
//    @ManyToOne
//    @Column(name = "Id")
//    private Users users;
}
