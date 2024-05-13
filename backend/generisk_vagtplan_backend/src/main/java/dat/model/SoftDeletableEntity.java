package dat.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
public abstract class SoftDeletableEntity
{
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;


    @Column(name = "deleted_on", nullable = true)
    LocalDateTime deletedOn;
}
