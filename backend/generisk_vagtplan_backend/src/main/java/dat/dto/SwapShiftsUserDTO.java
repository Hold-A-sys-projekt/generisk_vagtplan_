package dat.dto;

import dat.model.Shift;
import dat.model.SwapShifts;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SwapShiftsUserDTO {

    private Integer id;
    private Integer shift1Id;
    private LocalDateTime shift1Start;
    private LocalDateTime shift1End;
    private String shift1User;
    private Integer shift2Id;
    private LocalDateTime shift2Start;
    private LocalDateTime shift2End;
    private String shift2User;
    private String isAccepted;

    public SwapShiftsUserDTO(Integer id, Integer shift1Id, LocalDateTime shift1Start, LocalDateTime shift1End, String shift1User, Integer shift2Id, LocalDateTime shift2Start, LocalDateTime shift2End, String shift2User, String isAccepted) {
        this.id = id;
        this.shift1Id = shift1Id;
        this.shift1Start = shift1Start;
        this.shift1End = shift1End;
        this.shift1User = shift1User;
        this.shift2Id = shift2Id;
        this.shift2Start = shift2Start;
        this.shift2End = shift2End;
        this.shift2User = shift2User;
        this.isAccepted = isAccepted;
    }
}