package dat.dto;

import dat.model.SwapShifts;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SwapShiftsDTO implements DTO<SwapShifts> {

    private Integer id;
    private SwapRequestsDTO request;
    private String isAccepted;

    public SwapShiftsDTO(SwapShifts swapShifts) {
        this.id = swapShifts.getId();
        this.request = new SwapRequestsDTO(swapShifts.getRequest());
        this.isAccepted = swapShifts.getIsAccepted();
    }

    @Override
    public SwapShifts toEntity() {
        SwapShifts swapShifts = new SwapShifts();
        swapShifts.setId(this.id);
        swapShifts.setRequest(this.request.toEntity());
        swapShifts.setIsAccepted(this.isAccepted);
        return swapShifts;
    }
}
