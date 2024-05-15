package dat.model;


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
public class Employee extends SoftDeletableEntity implements dat.model.Entity<EmployeeDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
  
    private String role;


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
