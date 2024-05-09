package dat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "swapshifts")
public class SwapShifts implements dat.model.Entity<SwapShiftDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "shift_id1", referencedColumnName = "id")
    @JsonBackReference("shift-swapshift1")
    private Shift shift1;

    @ManyToOne
    @JoinColumn(name = "shift_id2", referencedColumnName = "id")
    @JsonBackReference("shift-swapshift2")
    private Shift shift2;

    String isAccepted = "";

    @Override
    public void setId(Object id) {
        this.id = (int) id;
    }

    @Override
    public SwapShiftDTO toDTO() {
        return new SwapShiftDTO(this);
    }

}