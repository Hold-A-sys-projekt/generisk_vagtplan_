package dat.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import dat.dto.SwapRequestsDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "swap_requests")
public class SwapRequests implements dat.model.Entity<SwapRequestsDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shift_id1", referencedColumnName = "id")
    @JsonBackReference("shift-swaprequest1")
    private Shift shift1;

    @ManyToOne
    @JoinColumn(name = "shift_id2", referencedColumnName = "id")
    @JsonBackReference("shift-swaprequest2")
    private Shift shift2;

    private String isAccepted = "";

    @Override
    public void setId(Object id) {
        this.id = (int) id;
    }

    @Override
    public SwapRequestsDTO toDTO() {
        return new SwapRequestsDTO(this);
    }
}