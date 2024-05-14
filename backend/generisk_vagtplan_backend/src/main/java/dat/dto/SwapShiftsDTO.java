package dat.dto;



import dat.model.SwapShifts;
import dat.model.Shift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SwapShiftsDTO implements DTO<SwapShifts> {

    private Integer id;
    private Shift shift1;
    private Shift shift2;
    private String isAccepted;

    public SwapShiftsDTO(SwapShifts entity) {
        this.id = entity.getId();
        this.shift1 = entity.getShift1();
        this.shift2 = entity.getShift2();
        this.isAccepted = entity.getIsAccepted();
    }

    public SwapShifts toEntity() {
        SwapShifts swap = new SwapShifts();
        swap.setShift1(this.shift1);
        swap.setShift2(this.shift2);
        swap.setIsAccepted(this.isAccepted);
        return swap;
    }
}
