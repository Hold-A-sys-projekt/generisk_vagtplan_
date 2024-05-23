package dat.dto;

import dat.dao.BuyRequestDAO;
import dat.model.BuyRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BuyRequestDTO implements DTO<BuyRequest>
{
    private Integer id;
    private Integer userId;
    private Integer shiftId;
    private LocalDateTime createdOn;

    public BuyRequestDTO(Integer id, Integer userId, Integer shiftId, LocalDateTime createdOn)
    {
        this.id = id;
        this.userId = userId;
        this.shiftId = shiftId;
        this.createdOn = createdOn;
    }

    public BuyRequestDTO(BuyRequest buy) {
        this(buy.getId() == null ? null : buy.getId(),
                buy.getUser().getId() == null ? null : buy.getUser().getId(),
                buy.getShift().getId(),
                buy.getCreatedOn() == null ? null : buy.getCreatedOn());
    }

    public BuyRequestDTO(Integer userId, Integer shiftId)
    {
        this.userId = userId;
        this.shiftId = shiftId;
    }

    @Override
    public BuyRequest toEntity()
    {
        BuyRequestDAO buyRequestDAO = BuyRequestDAO.getInstance();
        return buyRequestDAO.readById(this.id).orElse(null);
    }
}
