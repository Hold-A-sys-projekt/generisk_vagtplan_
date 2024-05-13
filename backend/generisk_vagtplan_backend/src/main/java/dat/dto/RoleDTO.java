package dat.dto;

import dat.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RoleDTO implements DTO<Role> {

    private String name;

    public RoleDTO(String name) {
        this.name = name;
    }

    @Override
    public Role toEntity() {
        return new Role(name);
    }
}
