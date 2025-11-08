package userService.registrations.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TeachrUser extends BaseModels{
private String teacherEmail;
private String role;
}
