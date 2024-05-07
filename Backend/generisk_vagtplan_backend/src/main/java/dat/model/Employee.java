package dat.model;

import dat.dto.EmployeeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@jakarta.persistence.Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Employee implements Serializable, dat.model.Entity<EmployeeDTO>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "employeename", unique = true, nullable = false, length = 25)
    @Setter
    private String employeename;

    @Column(name = "role", nullable = false)
    private String role;





    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    @Override
    public EmployeeDTO toDTO() {
        return new EmployeeDTO(this);
    }
}
