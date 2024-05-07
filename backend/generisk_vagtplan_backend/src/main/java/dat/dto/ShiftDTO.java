package dat.dto;

import dat.dao.ShiftDAO;
import dat.model.Shift;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ShiftDTO implements DTO<Shift> {
    private static final ShiftDAO DAO = ShiftDAO.getInstance();

    private Integer id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private Integer employeeId;

    public ShiftDTO(Integer id, LocalDateTime shiftStart, LocalDateTime shiftEnd, Integer employeeId) {
        this.id = id;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.employeeId = employeeId;
    }

    public ShiftDTO(Shift shift) {
        this.id = shift.getId();
        this.shiftStart = shift.getShiftStart();
        this.shiftEnd = shift.getShiftEnd();
        this.employeeId = shift.getEmployee().getId();
    }
    @Override
    public Shift toEntity() {
        ShiftDAO shiftDAO = ShiftDAO.getInstance();
        return DAO.readById(this.id).orElse(null);
    }
}
