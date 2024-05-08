package dat.dto;

import dat.dao.ExampleDAO;
import dat.dao.SwapShiftsDAO;
import dat.model.SwapShifts;
import dat.model.Shift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SwapShiftDTO implements DTO<SwapShifts> {

    private static final SwapShiftsDAO DAO = SwapShiftsDAO.getInstance();

    private Integer shiftId;
    private Shift shift1;
    private Shift shift2;
    private String isAccepted;

    public SwapShiftDTO(SwapShifts entity) {
        this(entity.getId(), entity.getShift1(), entity.getShift2(), entity.getIsAccepted());
    }

    @Override
    public SwapShifts toEntity() {
        return DAO.readById(this.shiftId).orElse(null);
    }
}

