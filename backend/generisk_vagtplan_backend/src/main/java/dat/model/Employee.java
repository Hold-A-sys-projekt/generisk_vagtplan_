package dat.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.dto.EmployeeDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "employees")
public class Employee implements dat.model.Entity<EmployeeDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore // Prevent infinite recursion
    private Set<Shift> shifts = new LinkedHashSet<>();

    public Employee(String name) {
        this.name = name;
    }


    @Override
    public void setId(Object id) {
    }

    @Override
    public EmployeeDTO toDTO() {
        return new EmployeeDTO(this);
    }
}
