package userService.entitis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
