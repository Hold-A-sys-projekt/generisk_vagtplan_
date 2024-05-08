package dat.model;

import dat.dto.ManagerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@jakarta.persistence.Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
public class Manager implements Serializable, dat.model.Entity<ManagerDTO>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "managername", unique = true, nullable = false, length = 25)
    @Setter
    private String managername;





    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    @Override
    public ManagerDTO toDTO() {
        return new ManagerDTO(this);
    }
}
