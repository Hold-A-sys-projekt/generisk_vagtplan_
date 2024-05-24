package dat.model;

import dat.dto.BuyRequestDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "buy_requests")
@NoArgsConstructor
@Getter
@Setter
public class BuyRequest implements dat.model.Entity<BuyRequestDTO>
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Shift shift;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;

    public BuyRequest(User user, Shift shift)
    {
        this.user = user;
        this.shift = shift;
        this.createdOn = LocalDateTime.now();
    }

    public BuyRequest(BuyRequestDTO buyRequestDTO)
    {
        this.id = buyRequestDTO.getId();
        this.createdOn = buyRequestDTO.getCreatedOn();
    }


    @Override
    public void setId(Object id)
    {
        this.id = (Integer) id;
    }

    @Override
    public BuyRequestDTO toDTO()
    {
        return new BuyRequestDTO(this);
    }
}
