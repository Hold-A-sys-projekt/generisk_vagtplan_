package dat.model;

import dat.dto.DepartmentDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "departments")
public class Department extends SoftDeletableEntity implements dat.model.Entity<DepartmentDTO>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    @ManyToOne()
    private Company company;

    public Department(String name, Company company) {
        this.name = name;
        this.company = company;
    }
    //TODO: This wasnt here but everything else
    // hints that a department has a list of users and vice versa
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public void setId(Object id) {
        if (!(id instanceof Integer)) {
            throw new IllegalArgumentException("Department id must be an integer");
        }

        this.id = (Integer) id;
    }

    @Override
    public DepartmentDTO toDTO() {
        return new DepartmentDTO(this);
    }
}
