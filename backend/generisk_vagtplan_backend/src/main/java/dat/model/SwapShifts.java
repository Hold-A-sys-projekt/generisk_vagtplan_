package dat.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.dto.SwapShiftsDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "swapshifts")
public class SwapShifts implements dat.model.Entity<SwapShiftsDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @JsonBackReference("swaprequest-swapshift")
    private SwapRequests request;

    private String isAccepted = "";

    @Override
    public void setId(Object id) {
        this.id = (int) id;
    }

    @Override
    public SwapShiftsDTO toDTO() {
        return new SwapShiftsDTO(this);
    }
}