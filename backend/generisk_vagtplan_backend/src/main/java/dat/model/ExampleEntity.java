package dat.model;

import dat.dto.ExampleDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
@AllArgsConstructor
public class ExampleEntity implements dat.model.Entity<ExampleDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @Override
    public void setId(Object id) {
        this.id = (Integer) id;
    }

    @Override
    public ExampleDTO toDTO() {
        return new ExampleDTO(this);
    }
}