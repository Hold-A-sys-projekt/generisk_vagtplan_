package dat.model;

import dat.dto.RouteRolesDTO;
import io.javalin.security.RouteRole;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
@NamedQueries(@NamedQuery(name = "Role.deleteAllRows", query = "DELETE FROM RouteRoles"))
@Getter
@NoArgsConstructor
public class RouteRoles implements Serializable, RouteRole, dat.model.Entity<RouteRolesDTO> {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "role_name", length = 20)
    private String name;

    @ManyToMany(mappedBy = "routeRoles", fetch = FetchType.EAGER)
    private final Set<User> userList = new LinkedHashSet<>();

    public RouteRoles(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        RouteRoles otherRouteRoles = (RouteRoles) other;
        return Objects.equals(this.name.toLowerCase(), otherRouteRoles.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public static RouteRoles of(String name) {
        return new RouteRoles(name);
    }

    @Override
    public void setId(Object id) {

    }

    @Override
    public RouteRolesDTO toDTO() {
        return new RouteRolesDTO(name);
    }
}