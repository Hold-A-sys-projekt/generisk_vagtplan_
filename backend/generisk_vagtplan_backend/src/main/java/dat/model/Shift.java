package dat.model;

import dat.dto.ShiftDTO;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shifts")

public class Shift implements dat.model.Entity<ShiftDTO>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shift_start")
    private LocalDateTime shiftStart;

    @Column(name = "shift_end")
    private LocalDateTime shiftEnd;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User employee;

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd, User employee){
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        setEmployee(employee);
    }

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd) {
    }

    @Override
    public void setId(Object id) {
        if (id != null) {
            this.id = Integer.parseInt(id.toString());
        }
    }

    @Override
    public ShiftDTO toDTO() {
        return new ShiftDTO(this);
    }

    public void setEmployee(User employee){
        this.employee=employee;
        employee.getShifts().add(this);

    }

    @Id
    public int getId() {
        return id;
    }
}
