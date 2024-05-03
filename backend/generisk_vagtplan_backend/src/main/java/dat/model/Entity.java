package dat.model;

import dat.dto.DTO;

@SuppressWarnings("rawtypes")
public interface Entity<DTOType extends DTO> {

    void setId(Object id);

    DTOType toDTO();
}