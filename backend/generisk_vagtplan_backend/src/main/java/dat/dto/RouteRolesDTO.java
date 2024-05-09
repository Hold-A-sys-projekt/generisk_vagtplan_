package dat.dto;

import dat.model.RouteRoles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class RouteRolesDTO implements DTO<RouteRoles> {
    private String name;

    public RouteRolesDTO(String name) {
        this.name = name;
    }

    @Override
    public RouteRoles toEntity() {
        return new RouteRoles();
    }
}
