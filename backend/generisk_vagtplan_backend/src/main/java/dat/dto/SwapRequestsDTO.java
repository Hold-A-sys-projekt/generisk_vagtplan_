package dat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import dat.model.Shift;
import dat.model.SwapRequests;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class SwapRequestsDTO implements DTO<SwapRequests> {

    private int id;
    private Shift shift1;
    private Shift shift2;
    private String isAccepted;
    private int requestedUserId;
    private String status;

    @JsonCreator
    public SwapRequestsDTO(
            @JsonProperty("id") int id,
            @JsonProperty("shift1") Shift shift1,
            @JsonProperty("shift2") Shift shift2,
            @JsonProperty("isAccepted") String isAccepted,
            @JsonProperty("requestedUserId") int requestedUserId,
            @JsonProperty("status") String status) {
        this.id = id;
        this.shift1 = shift1;
        this.shift2 = shift2;
        this.isAccepted = isAccepted;
        this.requestedUserId = requestedUserId;
        this.status = status;
    }

    public SwapRequestsDTO(SwapRequests entity) {
        this.id = entity.getId();
        this.shift1 = entity.getShift1();
        this.shift2 = entity.getShift2();
        this.isAccepted = entity.getIsAccepted();
        this.requestedUserId = entity.getRequestedUserId();
        this.status = entity.getStatus();
    }

    @Override
    public SwapRequests toEntity() {
        SwapRequests swap = new SwapRequests();
        swap.setShift1(this.shift1);
        swap.setShift2(this.shift2);
        swap.setIsAccepted(this.isAccepted);
        swap.setRequestedUserId(this.requestedUserId);
        swap.setStatus(this.status);
        return swap;
    }
}
