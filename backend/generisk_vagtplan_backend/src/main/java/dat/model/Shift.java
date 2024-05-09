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
public class Shift implements dat.model.Entity<ShiftDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "shift_start")
    private LocalDateTime shiftStart;

    @Column(name = "shift_end")
    private LocalDateTime shiftEnd;

    @Column(name = "punch_in")
    private LocalDateTime punchIn;

    @Column(name = "punch_out")
    private LocalDateTime punchOut;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Employee employee;

    private String status;

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd, Employee employee) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        setEmployee(employee);
    }

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }



    public void setEmployee(Employee employee) {
        this.employee = employee;
        if (employee != null && !employee.getShifts().contains(this)) {
            employee.getShifts().add(this);
        }
    }


    @Override
    public void setId(Object id) {
    }

    @Override
    public ShiftDTO toDTO() {
        return new ShiftDTO(this);
    }

    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", punchIn=" + punchIn +
                ", punchOut=" + punchOut +
                ", employeeId=" + employee.getId() +
                ", employeeName=" + employee.getName() +
                '}';
    }
}
