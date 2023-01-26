package shop.yesaladin.front.point.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointHistoryResponseDto {

    private Long id;
    private Long amount;
    private LocalDateTime createDateTime;
    private String pointCode;
    private String loginId;
}
