package dat.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Column(name = "shift_status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd, User user) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.setUser(user);
    }

    public Shift(LocalDateTime shiftStart, LocalDateTime shiftEnd) {
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
    }

    public void setUser(User user) {
        if (user != null && user.getId() != 0) {
            if ("employee".equals(user.getRole().getName())) {
                this.user = user;
            } else {
                throw new IllegalArgumentException("User must be an employee");
            }
        } else {
            throw new IllegalArgumentException("Invalid user");
        }
    }

    @Override
    public void setId(Object id) {
        if (id != null) {
            this.id = (int) id;
        }
    }

    @Override
    public ShiftDTO toDTO() {
        return new ShiftDTO(this);
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", shiftStart=" + shiftStart +
                ", shiftEnd=" + shiftEnd +
                ", punchIn=" + punchIn +
                ", punchOut=" + punchOut +
                ", userId=" + (user != null ? user.getId() : null) +
                ", userName=" + (user != null ? user.getUsername() : null) +
                '}';
    }
}
