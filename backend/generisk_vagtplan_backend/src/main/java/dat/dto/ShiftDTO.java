package dat.dto;

import dat.dao.ShiftDAO;
import dat.model.Shift;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import dat.model.Status;

import java.time.LocalDateTime;


@Getter
@ToString
@NoArgsConstructor
public class ShiftDTO implements DTO<Shift> {


    private Integer id;

    private LocalDateTime shiftStart;

    private LocalDateTime shiftEnd;

    private LocalDateTime punchIn;

    private LocalDateTime punchOut;

    private Integer userId;

    private String userName;

    private String userRole;

    private Status status;

    public ShiftDTO(Integer id, LocalDateTime shiftStart, LocalDateTime shiftEnd, LocalDateTime punchIn, LocalDateTime punchOut, Integer userId, Status status) {
        this.id = id;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
        this.userId = userId;
        this.status = status;

    }

    public ShiftDTO(Integer id, LocalDateTime shiftStart, LocalDateTime shiftEnd, LocalDateTime punchIn, LocalDateTime punchOut, Integer userId) {
        this.id = id;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.punchIn = punchIn;
        this.punchOut = punchOut;
        this.userId = userId;

    }

    public ShiftDTO(Shift shift) {
        this.id = shift.getId();
        this.shiftStart = shift.getShiftStart();
        this.shiftEnd = shift.getShiftEnd();
        this.punchIn = shift.getPunchIn();
        this.punchOut = shift.getPunchOut();
        this.userName = shift.getUser().getUsername();
        this.userRole = shift.getUser().getRole().getName();
        this.userId = shift.getUser() == null ? null : shift.getUser().getId();
        this.status = shift.getStatus();
    }

    @Override
    public Shift toEntity() {
        ShiftDAO shiftDAO = ShiftDAO.getInstance();
        Shift shift = shiftDAO.readById(this.id).orElse(new Shift());
        shift.setShiftStart(this.shiftStart);
        shift.setShiftEnd(this.shiftEnd);
        shift.setPunchIn(this.punchIn);
        shift.setPunchOut(this.punchOut);
        return shift;
    }
}
