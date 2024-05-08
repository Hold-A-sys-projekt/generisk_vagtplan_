package dat.model;

import dat.dto.SwapShiftDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "swap_shifts")
public class SwapShifts implements dat.model.Entity<SwapShiftDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shift_id1", referencedColumnName = "id")
    private Shift shift1;

    @ManyToOne
    @JoinColumn(name = "shift_id2", referencedColumnName = "id")
    private Shift shift2;

    private String isAccepted = "";

    @Override
    public void setId(Object id) {
        this.id = (int) id;
    }

    @Override
    public SwapShiftDTO toDTO() {
        return new SwapShiftDTO(this);
    }
}
