package dat.dto;

import dat.model.Entity;

@SuppressWarnings("rawtypes")
public interface DTO<EntityType extends Entity> {

    EntityType toEntity();
}