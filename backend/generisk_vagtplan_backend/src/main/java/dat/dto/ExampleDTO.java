package dat.dto;

import dat.dao.ExampleDAO;
import dat.model.ExampleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExampleDTO implements DTO<ExampleEntity> {

    private static final ExampleDAO DAO = ExampleDAO.getInstance();

    private Integer id;
    private String name;
    private String description;

    public ExampleDTO(ExampleEntity entity) {
        this(entity.getId(), entity.getName(), entity.getDescription());
    }

    @Override
    public ExampleEntity toEntity() {
        return DAO.readById(this.id).orElse(null);
    }
}