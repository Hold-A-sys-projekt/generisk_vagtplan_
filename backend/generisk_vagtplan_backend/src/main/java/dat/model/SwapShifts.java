package dat.model;

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
public class SwapShifts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shift_id1", referencedColumnName = "id")
    private Shift shift1;

    @ManyToOne
    @JoinColumn(name = "shift_id2", referencedColumnName = "id")
    private Shift shift2;

    private boolean isAccepted = false;
}
